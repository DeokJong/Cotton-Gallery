"use client";

import { baseUrl } from "@/app/(auth)/_components/SignUp";
import { Item } from "@/app/(main)/_components/GoodsCard";
import CartItem from "@/app/cart/_components/CartItem";
import { getItem } from "@/app/items/_components/ItemDetail";
import { useAuthStore } from "@/store/authStore";
import { useSearchParams } from "next/navigation";
import React, { useEffect, useState } from "react";
import { RxCross2 } from "react-icons/rx";
import AddAddressModal from "./AddAddressModal";
import { refreshToken } from "@/app/(main)/_components/Home";

type Address = {
  addressId: number;
  zipcode: string;
  street: string;
  detail: string;
  addressType?: string;
};

const changePrimaryAddress = async (addressId: number) => {
  try {
    const response = await fetch(`${baseUrl}/api/user/address/${addressId}/primary`, {
      method: "PATCH",
      credentials: "include",
      headers: {
        Accept: "application/json",
        "Content-Type": "application/json"
      }
    });

    if (!response.ok) {
      throw new Error(`Failed to set primary address: ${response.statusText}`);
    }

    const data = await response.json();
    return data;
  } catch (error) {
    console.error("Error updating primary address:", error);
    return null;
  }
};

const createOrder = async (addressId: number, itemId: number, count: number) => {
  try {
    const response = await fetch(`${baseUrl}/api/user/orders`, {
      method: "POST",
      credentials: "include",
      headers: {
        Accept: "application/json",
        "Content-Type": "application/json"
      },
      body: JSON.stringify({
        addressId,
        orderItemCreateRequestList: [{ itemId, count }]
      })
    });

    if (!response.ok) {
      alert("주문을 실패했습니다.");
      throw new Error(`Failed to create order: ${response.statusText}`);
    }

    const data = await response.json();
    alert("주문을 완료했습니다.");
    return data;
  } catch (error) {
    console.error("Error creating order:", error);
    return null;
  }
};

const Ordersheet = () => {
  const searchParams = useSearchParams();
  const itemId = Number(searchParams.get("itemId"));
  const count = Number(searchParams.get("count"));
  const { zipcode, street, detail, error, setZipcode, setStreet, setDetail } = useAuthStore();
  const [primaryAddr, setPrimaryAddr] = useState<Address>();
  const [primaryAddrId, setPrimaryAddrId] = useState<number>();
  const [addressList, setAddressList] = useState<Address[]>();
  const [item, setItem] = useState<Item>();
  const [changeComponent, setChangeComponent] = useState<string>("default");
  const [isAddBtnClicked, setIsAddBtnClicked] = useState<boolean>(false);
  const [isModalOpen, setIsModalOpen] = useState<boolean>(false);

  const fetchPrimaryAddress = async () => {
    try {
      const response = await fetch(`${baseUrl}/api/user/address/primary`, {
        method: "GET",
        credentials: "include",
        headers: {
          Accept: "application/json"
        }
      });
      if (!response.ok) {
        throw new Error(`Failed to fetch primary address: ${response.statusText}`);
      }
      const result = await response.json();

      if (result.data) {
        setPrimaryAddr(result.data);
        setPrimaryAddrId(result.data.addressId);
      } else {
        console.warn("No address data received.");
      }
      return result;
    } catch (error) {
      console.error("Error fetching primary address:", error);
      return null;
    }
  };

  const fetchAddressList = async () => {
    try {
      const response = await fetch(`${baseUrl}/api/user/address`, {
        method: "GET",
        credentials: "include",
        headers: {
          Accept: "application/json"
        }
      });

      if (!response.ok) {
        throw new Error(`Failed to fetch user address: ${response.statusText}`);
      }

      const result = await response.json();
      setAddressList(result.data.address);
      return result;
    } catch (error) {
      console.error("Error fetching user address:", error);
      return null;
    }
  };
  // 배송지 변경 버튼 누르면 주소 리스트 조회
  const handleAddrList = () => {
    fetchAddressList();
    setChangeComponent("change");
  };
  // 선택 후에 변경 요청
  const EditPrimaryAddr = async () => {
    try {
      const response = await fetch(`${baseUrl}/api/user/address/${(primaryAddrId as number) + 1}/primary`, {
        method: "PATCH",
        credentials: "include",
        headers: {
          Accept: "application/json"
        }
      });

      if (!response.ok) {
        throw new Error(`Failed to set primary address: ${response.statusText}`);
      }

      const data = await response.json();
      return data;
    } catch (error) {
      console.error("Error updating primary address:", error);
      return null;
    }
  };

  const addAddress = async () => {
    if (!zipcode || !street || !detail) {
      console.error("All fields (zipcode, street, detail) must be provided.");
      alert("우편번호, 주소, 상세 주소를 모두 입력해주세요.");
      return null;
    }

    try {
      const response = await fetch(`${baseUrl}/api/user/address`, {
        method: "POST",
        credentials: "include",
        headers: {
          Accept: "application/json",
          "Content-Type": "application/json"
        },
        body: JSON.stringify({
          zipcode,
          street,
          detail
        })
      });

      if (!response.ok) {
        throw new Error(`Failed to add address: ${response.statusText}`);
      }

      const data = await response.json();
      return data;
    } catch (error) {
      console.error("Error adding address:", error);
      return null;
    }
  };

  const deleteAddr = async (addressId: number, addressType: string) => {
    if (addressType === "PRIMARY") {
      alert("기본 배송지는 삭제할 수 없습니다.");
      return;
    }
    try {
      const response = await fetch(`${baseUrl}/api/user/address/${addressId}`, {
        method: "DELETE",
        credentials: "include",
        headers: {
          Accept: "application/json"
        }
      });

      if (!response.ok) {
        throw new Error(`Failed to delete address: ${response.statusText}`);
      }

      const data = await response.json();
      alert("주소가 정상적으로 삭제되었습니다.");
      setAddressList((prevAddressList) => prevAddressList?.filter((address) => address.addressId !== addressId));
      return data;
    } catch (error) {
      console.error("Error deleting address:", error);
      return null;
    }
  };

  const fetchItem = async (itemId: number) => {
    const result = await getItem(itemId);
    if (result) {
      setItem(result.data);
    }
  };

  const handleClickOrderBtn = (e: React.MouseEvent<HTMLButtonElement>) => {
    e.preventDefault();
    createOrder(primaryAddrId as number, itemId, count);
  };

  const handleAddressBtnClick = () => {
    setIsModalOpen(true);
  };

  const handleDetailChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setDetail(e.target.value);
  };

  useEffect(() => {
    fetchPrimaryAddress();
    refreshToken();
  }, []);

  return (
    <div className=" flex flex-col items-center justify-center">
      <h1 className=" w-full h-16 border-b-2 text-center border-gray-200 text-[1.75rem] mb-6">주문서</h1>
      {changeComponent === "default" ? (
        <>
          <div className="w-[48.75rem]">
            <div className="w-[48.75rem] flex justify-between items-center">
              <p className="text-xl font-semibold">기본 배송지</p>
              <button className="w-24 border-2 border-gray-400 rounded" onClick={handleAddrList}>
                배송지 변경
              </button>
            </div>
            <div className="mt-5 p-3 bg-gray-100">
              <p>{primaryAddr?.street}</p>
              <p>({primaryAddr?.detail})</p>
              <p>우편번호: {primaryAddr?.zipcode}</p>
            </div>
          </div>
          <button className="w-[48.75rem] h-10 mt-6  mb-5 border-2" onClick={handleClickOrderBtn}>
            주문하기
          </button>
        </>
      ) : (
        <div className="w-[48.75rem] flex flex-col items-center">
          <div className="w-[48.75rem] flex items-center justify-between">
            <p className="text-xl font-semibold">배송지 정보</p>
            <button onClick={() => setChangeComponent("default")}>
              <RxCross2 size={25} />
            </button>
          </div>
          <button className="w-full h-10 mt-6  mb-5 border-2" onClick={() => setIsAddBtnClicked((prev) => !prev)}>
            배송지 추가하기
          </button>
          {isAddBtnClicked && (
            <div className="w-[48.75rem] text-xl">
              <label htmlFor="address" className="ml-3">
                주소
              </label>
              <div className="flex justify-between mt-2">
                <input
                  id="address"
                  type="text"
                  alt="주소"
                  placeholder="주소"
                  value={street}
                  className="w-[38.5rem] h-[3.75rem] indent-5 rounded-[35px] bg-gray-200 focus:outline-none"
                  readOnly
                />
                <button
                  type="button"
                  onClick={handleAddressBtnClick}
                  className="w-[9.375rem] h-[3.438rem]  rounded-lg bg-gray-300"
                >
                  우편번호 검색
                </button>
              </div>
              <input
                id="addressDetail"
                type="text"
                alt="상세 주소"
                placeholder="상세 주소"
                value={detail}
                onChange={handleDetailChange}
                className="w-[48.75rem] h-[3.75rem] mt-2 indent-5 rounded-[35px] bg-gray-200"
              />
              <p className="pt-1 indent-3 text-sm text-red-500">{error.street}</p>
              <button onClick={addAddress} className="w-full h-10 mt-1  mb-5 rounded bg-gray-300">
                추가
              </button>
              <br className="border-2" />
              <AddAddressModal
                isModalOpen={isModalOpen}
                setIsModalOpen={setIsModalOpen}
                setZipcode={setZipcode}
                setStreet={setStreet}
              />
            </div>
          )}
          <ul className="w-[48.75rem] flex flex-col items-center justify-between">
            {addressList?.map((address, index) => (
              <li
                key={index}
                className={`mb-[1.25rem] w-[48.75rem] bg-gray-200 p-3 ${primaryAddrId === address.addressId && "bg-gray-300"}`}
                onClick={() => setPrimaryAddrId(address.addressId)}
              >
                <div>
                  {address?.addressType === "PRIMARY" && <p className="text-base font-semibold mb-2">기본 배송지</p>}
                  <p>{address?.street}</p>
                  <p>({address?.detail})</p>
                  <div className="flex justify-between">
                    <p>우편번호: {address?.zipcode}</p>
                    <button
                      className=" w-12 h-7 rounded-md bg-gray-300"
                      onClick={() => deleteAddr(address.addressId, address.addressType as string)}
                    >
                      삭제
                    </button>
                  </div>
                </div>
              </li>
            ))}
          </ul>
          <button className="w-full h-10 mb-5  border-2 " onClick={EditPrimaryAddr}>
            변경하기
          </button>
        </div>
      )}
    </div>
  );
};

export default Ordersheet;

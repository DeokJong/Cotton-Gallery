"use client";

import { baseUrl } from "@/app/(auth)/_components/SignUp";
import { Item } from "@/app/(main)/_components/GoodsCard";
import CartItem from "@/app/cart/_components/CartItem";
import { getItem } from "@/app/items/_components/ItemDetail";
import { useAuthStore } from "@/store/authStore";
import { useSearchParams } from "next/navigation";
import React, { useEffect, useState } from "react";
import { RxCross2 } from "react-icons/rx";

type Address = {
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
    console.log("Primary address updated successfully:", data);
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
    console.log("Order created successfully:", data);
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
  const { zipcode, street, detail, setZipcode, setStreet, setDetail } = useAuthStore();
  const [addressId, setAddressId] = useState<number>(1);
  const [primaryAddr, setPrimaryAddr] = useState<Address>();
  const [primaryAddrId, setPrimaryAddrId] = useState<number>();
  const [addressList, setAddressList] = useState<Address[]>();
  const [item, setItem] = useState<Item>();
  const [changeComponent, setChangeComponent] = useState<string>("default");
  // 기본 주소 조회

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
      console.log("Primary Address:", result.data);

      if (result.data && Array.isArray(result.data.address)) {
        setPrimaryAddr(result.data);

        // 주소 리스트 길이가 1일 때 primaryAddrId를 1로 설정
        if (result.data.address.length === 1) {
          setPrimaryAddrId(1);
        } else {
          const primaryIndex = result.data.address.findIndex((item: Address) => item.addressType === "PRIMARY");

          if (primaryIndex !== -1) {
            setPrimaryAddrId(primaryIndex);
          } else {
            console.warn("No PRIMARY address found.");
          }
        }
      } else {
        console.warn("Invalid address data format.");
      }
      setPrimaryAddr(result.data);
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
      console.log("User Address:", result.data);
      setAddressList(result.data.address);
      return result;
    } catch (error) {
      console.error("Error fetching user address:", error);
      return null;
    }
  };
  // 배송지 변경  버튼 누르면 주소 리스트 조회
  const handleAddrList = () => {
    fetchAddressList();
    setChangeComponent("change");
  };
  // 선택 후에 변경 요청
  const EditPrimaryAddr = async () => {
    try {
      const response = await fetch(`${baseUrl}/api/user/address/${primaryAddrId}/primary`, {
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
      console.log("Primary address updated successfully:", data);
      return data;
    } catch (error) {
      console.error("Error updating primary address:", error);
      return null;
    }
  };

  const addAddress = async () => {
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
      console.log("Address added successfully:", data);
      return data;
    } catch (error) {
      console.error("Error adding address:", error);
      return null;
    }
  };

  const fetchItem = async (itemId: number) => {
    const result = await getItem(itemId);
    if (result) {
      console.log("아이템", result.data);
      setItem(result.data);
    }
  };

  const handleClickOrderBtn = (e: React.MouseEvent<HTMLButtonElement>) => {
    e.preventDefault();
    createOrder(addressId, itemId, count);
  };

  useEffect(() => {
    // 기본 주소 조회
    fetchPrimaryAddress();
  }, []);
  return (
    <div className=" flex flex-col items-center justify-center">
      <h1 className=" w-full h-16 border-b-2 text-center border-gray-200 text-[1.75rem] mb-6">주문서</h1>

      {changeComponent === "default" ? (
        <>
          <div className="w-[48.75rem] h-[10rem] bg-gray-100">
            <div className="w-[48.75rem] flex justify-between items-center">
              <p className="text-xl font-semibold">기본 배송지</p>
              <button className="w-24 border-2 border-gray-400 rounded" onClick={handleAddrList}>
                배송지 변경
              </button>
            </div>
            <div>
              <p>d{primaryAddr?.street}</p>
              <p>d({primaryAddr?.detail})</p>
              <p>우편번호: {primaryAddr?.zipcode}</p>
            </div>
          </div>
          <button onClick={handleClickOrderBtn}>주문하기</button>
        </>
      ) : (
        <div className="w-[48.75rem] flex flex-col items-center">
          <div className="w-[48.75rem] flex items-center justify-between">
            <p className="text-xl font-semibold">배송지 정보</p>
            <button onClick={() => setChangeComponent("default")}>
              <RxCross2 size={25} />
            </button>
          </div>
          {/* 모달 */}
          <button>배송지 추가하기</button>
          <ul className="w-[48.75rem] flex flex-col items-center justify-between">
            {addressList?.map((address, index) => (
              <li
                key={index}
                className={`mb-[1.25rem] w-[48.75rem] bg-gray-200 p-3 ${primaryAddrId === index && "bg-gray-400"}`}
                onClick={() => setPrimaryAddrId(index)}
              >
                <div>
                  {address?.addressType === "PRIMARY" && <p className="text-base font-semibold mb-2">기본 배송지</p>}
                  <p>{address?.street}</p>
                  <p>({address?.detail})</p>
                  <p>우편번호: {address?.zipcode}</p>
                </div>
              </li>
            ))}
          </ul>
          <button onClick={EditPrimaryAddr}>변경하기</button>
        </div>
      )}
    </div>
  );
};

export default Ordersheet;

"use client";

import { Item } from "@/app/(main)/_components/GoodsCard";
import Image from "next/image";
import React, { useEffect, useState } from "react";
import { FaHeart, FaRegHeart } from "react-icons/fa";
import { FaChevronLeft } from "react-icons/fa6";
import { FaChevronRight } from "react-icons/fa6";
import { RxCross2 } from "react-icons/rx";
import { FaMinus } from "react-icons/fa6";
import { FaPlus } from "react-icons/fa6";
import DeleteItemModal from "./DeleteItemModal";
import Link from "next/link";
import { useAuthStore } from "@/store/authStore";
import { baseUrl } from "@/app/(auth)/_components/SignUp";
import { useRouter } from "next/navigation";

type ItemDetailPropsType = {
  itemId: number;
};

export const getItem = async (itemId: number) => {
  try {
    const response = await fetch(`${baseUrl}/api/public/items/${itemId}`, {
      method: "GET",
      credentials: "include",
      headers: {
        Accept: "application/json"
      }
    });

    if (!response.ok) {
      throw new Error("아이템을 가져오는 데 실패했습니다.");
    }

    const data = await response.json();
    console.log(data);
    return data;
  } catch (error) {
    console.error("Error fetching item list:", error);
    return null;
  }
};

const ItemDetail = ({ itemId }: ItemDetailPropsType) => {
  const router = useRouter();
  const { name } = useAuthStore();
  const [isModalOpen, setIsModalOpen] = useState<boolean>(false);
  const [item, setItem] = useState<Item>();
  const [count, setCount] = useState<number>(0);
  const [likeCount, setLikeCount] = useState<number>(item?.likeCount as number);
  const [liked, setLiked] = useState<boolean>(item?.likedByMe as boolean);

  //const deliveryFee = 3000;
  useEffect(() => {
    if (item) {
      setLiked(item.likedByMe);
      setLikeCount(item.likeCount);
    }
  }, [item]);

  const fetchItem = async (itemId: number) => {
    const result = await getItem(itemId);
    if (result) {
      console.log("아이템", result.data);
      setItem(result.data);
    }
  };

  const handleCartBtn = async () => {
    if (count === 0) {
      alert("상품의 수량을 선택해주세요.");
      return;
    }
    try {
      const response = await fetch(`${baseUrl}/api/user/cartItem`, {
        method: "POST",
        credentials: "include",
        headers: {
          "Content-Type": "application/json"
        },
        body: JSON.stringify({
          quantity: count,
          itemId
        })
      });

      const result = await response.json();
      console.log(result);

      if (response.ok) {
        alert("장바구니에 상품을 담았습니다.");
      } else {
        alert(`장바구니에 상품 담기 실패: ${result.message}`);
      }
    } catch (error) {
      console.error("요청 중 오류 발생:", error);
      alert("장바구니 상품을 담는 중 오류가 발생했습니다.");
    }
  };

  const toggleLike = async (e: React.MouseEvent<HTMLButtonElement>) => {
    e.preventDefault();
    try {
      const method = liked ? "PATCH" : "POST";
      const response = await fetch(`${baseUrl}/api/user/likes/${item?.itemId}`, {
        method,
        credentials: "include",
        headers: {
          "Content-Type": "application/json"
        },
        body: JSON.stringify({ itemId: item?.itemId })
      });

      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }

      const result = await response.json();

      if (result.status === 201) {
        setLiked(true);
        setLikeCount((likeCount as number) + 1);
      } else if (result.status === 200) {
        setLiked(false);
        setLikeCount((likeCount as number) - 1);
      } else {
        console.warn("Unexpected response status:", result.status);
      }
      console.log(result);
    } catch (error) {
      console.error("Failed to toggle like status:", error);
    }
  };

  const handleBuyBtn = async (e: React.MouseEvent<HTMLButtonElement>, itemId: number, count: number) => {
    e.preventDefault();
    if (count === 0) {
      alert("상품의 수량을 선택해주세요.");
      return;
    }
    router.push(`/ordersheet?itemId=${itemId}&count=${count}`);
  };

  useEffect(() => {
    fetchItem(itemId);
    // eslint-disable-next-line
  }, []);

  return (
    <div className="flex flex-col items-center h-screen overflow-y-auto">
      <div className="flex gap-5">
        <div className="flex items-center gap-5">
          <button className="invisible">
            <FaChevronLeft size={24} />
          </button>
          <Image
            src={`${baseUrl}/api/public/image?filename=${item?.itemImage}&imageType=ITEM_IMAGE`}
            width={500}
            height={500}
            priority
            alt="상품 사진"
          />
          <button className="invisible">
            <FaChevronRight size={24} />
          </button>
        </div>
        <div className="w-[36.25rem]">
          <div className="flex justify-between">
            <p className="text-xl mb-4 invisible">판매자</p>
            <div className={`flex text-bold text-white gap-3 ${name !== "Admin" && "invisible"}`}>
              <DeleteItemModal isModalOpen={isModalOpen} setIsModalOpen={setIsModalOpen} itemId={item?.itemId} />
              <button onClick={() => setIsModalOpen(true)} className="w-16 h-9 rounded-md bg-gray-400">
                삭제
              </button>
              <Link href={`/items/edit/${itemId}`}>
                <button className="w-16 h-9 rounded-md bg-gray-400">수정</button>
              </Link>
            </div>
          </div>
          <p className="text-[1.625rem] mb-4">{item?.name}</p>
          <div className="flex items-center gap-2 mb-10">
            <FaHeart size={24} />
            <p className="text-xl">{likeCount}</p>
          </div>
          <p className="text-[2.188rem] font-bold mb-10">{item?.price.toLocaleString()}원</p>
          {/* <div className="flex justify-between w-[12.5rem] mb-4">
            <p>배송비</p>
            <p>{deliveryFee.toLocaleString()}원</p>
          </div> */}
          <div className="flex flex-col p-3 justify-center gap-5 w-[33.438rem] h-[6.25rem] bg-gray-200">
            <div className="flex justify-between">
              <p className="w-[30rem] mt-1 ml-1 truncate">{item?.name}</p>
              <button>
                <RxCross2 className="cursor-pointer" size={24} />
              </button>
            </div>
            <div className="flex justify-between">
              <div className="flex justify-between items-center border-2 border-gray-400 w-[6.25rem]">
                <button onClick={() => setCount((prev) => (prev > 0 ? prev - 1 : prev))} className="ml-1">
                  <FaMinus />
                </button>
                <p className=" text-center w-[2.813rem] font-[0.938rem] border-l-2 border-r-2 border-gray-400">
                  {count}
                </p>
                <button onClick={() => setCount((prev) => prev + 1)} className="mr-1">
                  <FaPlus />
                </button>
              </div>
              <p className="text-xl font-semibold">{((item?.price as number) * count).toLocaleString()}원</p>
            </div>
          </div>
          <div className="w-[33.438rem] flex justify-end">
            <div className="flex justify-between items-center w-[10.625rem] h-[5.313rem]">
              <p className="text-lg">합계</p>
              <p className="text-3xl font-bold text-sky-400">
                {count !== 0 ? ((item?.price as number) * count).toLocaleString() : 0}원
              </p>
            </div>
          </div>
          <div className="flex justify-between w-[33.438rem] h-[3.125rem] text-[1.563rem] font-bold text-white">
            <div className="flex justify-center items-center w-[4.688rem] border-[0.188rem] border-gray-400 border-r-0">
              <button onClick={toggleLike}>
                {liked ? <FaHeart color="red" size={30} /> : <FaRegHeart size={30} color="gray" />}
              </button>
            </div>
            <button onClick={handleCartBtn} className="w-[14.375rem] bg-black">
              장바구니
            </button>
            <button onClick={(e) => handleBuyBtn(e, itemId, count)} className="w-[14.375rem] bg-sky-400">
              바로구매
            </button>
          </div>
        </div>
      </div>
      <div className="w-[68.75rem]">
        <Image
          src={`${baseUrl}/api/public/image?filename=${item?.itemInfoImage}&imageType=ITEM_INFO_IMAGE`}
          alt="상품 상세 정보"
          width={1100}
          height={1920}
          priority
        />
      </div>
    </div>
  );
};

export default ItemDetail;

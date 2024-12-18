"use client";

import { NEXT_PUBLIC_API_URL } from "@/constants";
import Image from "next/image";
import React, { useState } from "react";
import { FaHeart } from "react-icons/fa";
import { FaRegHeart } from "react-icons/fa";
import { HiOutlineShoppingCart } from "react-icons/hi";

interface DiscountResponse {
  discountId: number; // 할인 고유 ID
  name: string; // 할인 이름
  discountPercent: number; // 할인 퍼센트
  startDate: string; // 할인 시작 날짜 (ISO 형식)
  endDate: string; // 할인 종료 날짜 (ISO 형식)
}

export type Item = {
  itemId: number; // 아이템 고유 ID
  name: string; // 아이템 이름
  price: number; // 가격
  stockQuantity: number; // 재고 수량
  likeCount: number; // 좋아요 개수
  likedByMe: boolean; // 내가 좋아요를 눌렀는지 여부
  discountResponse: DiscountResponse | null; // 할인 정보 (null일 수도 있음)
};

type PropsType = {
  item: Item;
};

const GoodsCard = ({ item }: PropsType) => {
  const [liked, setLiked] = useState<boolean>(item.likedByMe);

  const toggleLike = async (e: React.MouseEvent<HTMLButtonElement>) => {
    e.preventDefault();
    // 좋아요 취소 patch cors 설정 질문
    try {
      const method = liked ? "PATCH" : "POST";
      const response = await fetch(`${NEXT_PUBLIC_API_URL}/api/user/likes/${item.itemId}`, {
        method,
        credentials: "include",
        headers: {
          "Content-Type": "application/json"
        },
        body: JSON.stringify({ itemId: item.itemId })
      });

      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }

      const result = await response.json();

      if (result.status === 201) {
        setLiked((prev) => !prev);
      } else {
        console.warn("Unexpected response status:", result.status);
      }
      console.log(result);
    } catch (error) {
      console.error("Failed to toggle like status:", error);
    }

    // e.preventDefault();

    // if (!liked) {
    //   const response = await fetch(`${NEXT_PUBLIC_API_URL}/api/user/likes/${item.itemId}`, {
    //     method: "POST",
    //     credentials: "include",
    //     headers: {
    //       "Content-Type": "application/json"
    //     },
    //     body: JSON.stringify({
    //       itemId: item.itemId
    //     })
    //   });
    //   const postResult = await response.json();
    //   if (postResult.status === 201) {
    //     setLiked((prev) => !prev);
    //   }
    //   console.log(postResult);
    // }

    // if (liked) {
    //   const response = await fetch(`${NEXT_PUBLIC_API_URL}/api/user/likes/${item.itemId}`, {
    //     method: "PATCH",
    //     credentials: "include",
    //     headers: {
    //       "Content-Type": "application/json"
    //     },
    //     body: JSON.stringify({
    //       itemId: item.itemId
    //     })
    //   });
    //   const patchResult = await response.json();
    //   if (patchResult.status === 201) {
    //     setLiked((prev) => !prev);
    //   }
    //   console.log(patchResult);
    //   console.log(liked, item.itemId);
    // }
  };

  return (
    <div className="w-[23.75rem] h-[13.125rem] border-2 rounded-sm">
      <div className="flex flex-col">
        <div className="flex">
          <Image src={"/pokeball.png"} width={165} height={165} priority alt="상품 사진" />
          <div className="w-[13.188rem] flex flex-col justify-between border-l-2">
            <div className="ml-1 mt-1">
              <p className="text-base">판매자</p>
              <p className="text-lg">{item.name}</p>
              <p className="font-bold text-xl">{item.price.toLocaleString()}원</p>
            </div>
            <div className="w-full h-[35px] pr-2 flex justify-end items-center border-t-2">
              <button onClick={toggleLike}>{liked ? <FaHeart color="red" /> : <FaRegHeart />}</button>
              <button className="ml-2">
                {/* TODO: 장바구니 담기*/}
                <HiOutlineShoppingCart />
                {/* <HiShoppingCart /> */}
              </button>
            </div>
          </div>
        </div>
        <div className="w-full h-[45px] pl-2 pr-2 flex justify-between items-center border-t-2">
          <p>찜 {item.likeCount}</p>
          <p>#해시태그</p>
        </div>
      </div>
    </div>
  );
};

export default GoodsCard;

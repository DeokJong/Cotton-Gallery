"use client";

import { baseUrl } from "@/app/(auth)/_components/SignUp";
import Image from "next/image";
import React, { useEffect, useState } from "react";
import { FaHeart } from "react-icons/fa";
import { FaRegHeart } from "react-icons/fa";

interface DiscountResponse {
  discountId: number;
  name: string;
  discountPercent: number;
  startDate: string;
  endDate: string;
}

export type Item = {
  discountResponse: DiscountResponse | null;
  itemId: number;
  itemImage: string;
  itemInfoImage?: string;
  likeCount: number;
  likedByMe: boolean;
  name: string;
  price: number;
  stockQuantity: number;
};

type PropsType = {
  item: Item;
};

const GoodsCard = ({ item }: PropsType) => {
  const [liked, setLiked] = useState<boolean>(item.likedByMe);
  const [likeCount, setLikeCount] = useState<number>(item.likeCount);

  const toggleLike = async (e: React.MouseEvent<HTMLButtonElement>) => {
    e.preventDefault();
    try {
      const method = liked ? "PATCH" : "POST";
      const response = await fetch(`${baseUrl}/api/user/likes/${item.itemId}`, {
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
        setLiked(true);
        setLikeCount((prev) => prev + 1);
      } else if (result.status === 200) {
        setLiked(false);
        setLikeCount((prev) => prev - 1);
      } else {
        console.warn("Unexpected response status:", result.status);
      }
    } catch (error) {
      console.error("Failed to toggle like status:", error);
    }
  };

  return (
    <div className="w-[23.75rem] h-[13.125rem] border-2 rounded-sm">
      <div className="flex flex-col">
        <div className="flex">
          <Image
            src={`${baseUrl}/api/public/image?filename=${item.itemImage}&imageType=ITEM_IMAGE`}
            width={165}
            height={165}
            priority
            alt="상품 사진"
          />
          <div className="w-[13.188rem] flex flex-col justify-between border-l-2">
            <div className="ml-1 mt-1 p-1">
              <p className="text-base hidden">판매자</p>
              <p className="text-lg">{item.name}</p>
              <p className="font-bold text-xl">{item.price.toLocaleString()}원</p>
            </div>
            <div className="w-full h-[35px] pr-2 flex justify-end items-center border-t-2">
              <button onClick={toggleLike}>{liked ? <FaHeart color="red" /> : <FaRegHeart />}</button>
            </div>
          </div>
        </div>
        <div className="w-full h-[45px] pl-2 pr-2 flex justify-between items-center border-t-2">
          <p>찜 {likeCount}</p>
          <p className="invisible">#해시태그</p>
        </div>
      </div>
    </div>
  );
};

export default GoodsCard;

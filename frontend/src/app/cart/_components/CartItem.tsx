"use client";

import React, { Dispatch, SetStateAction, useEffect, useState } from "react";
import { CartItemType } from "./Cart";
import { FaMinus, FaPlus } from "react-icons/fa";
import { baseUrl } from "@/app/(auth)/_components/SignUp";
import DeleteCartItemModal from "./DeleteCartItemModal";
import Link from "next/link";

type PropsType = {
  item: CartItemType;
  setItems: Dispatch<SetStateAction<CartItemType[]>>;
};

const CartItem = ({ item, setItems }: PropsType) => {
  const [isModalOpen, setIsModalOpen] = useState<boolean>(false);
  const [quantity, setQuantity] = useState<number>(item.quantity);

  const handleItemQuantity = async (e: React.MouseEvent<HTMLButtonElement>, cartItemId: number) => {
    e.preventDefault();
    const { id } = e.currentTarget;
    try {
      const response = await fetch(`${baseUrl}/api/user/cartItem/${cartItemId}?quantityChangeType=${id}`, {
        method: "PATCH",
        credentials: "include",
        headers: {
          "Content-Type": "application/json"
        }
      });
      const result = await response.json();

      if (response.ok) {
        setQuantity(id === "DECREASE" ? Math.max(quantity - 1, 0) : quantity + 1);
      } else {
        alert(`장바구니 수량 변경 실패: ${result.message}`);
      }
    } catch (error) {
      console.error("요청 중 오류 발생:", error);
      alert("장바구니 수량 변경중 오류가 발생했습니다.");
    }
  };

  return (
    <div className="w-[48.75rem] h-[6.25rem] border-2 rounded-sm">
      <div className="flex flex-col">
        <div className="flex">
          <div className="w-[48.75rem] flex flex-col justify-between">
            <Link href={`/items/detail/${item.cartItemId}`} className="ml-2 mt-2">
              <p className="text-base hidden">판매자</p>
              <p className="text-lg">{item.name}</p>
              <p className="font-bold text-xl">{item.price.toLocaleString()}원</p>
            </Link>
            <div className="w-full h-[30px] pr-2 flex justify-between items-center">
              <div className="ml-2 flex justify-center items-center gap-2">
                <button id="DECREASE" onClick={(e) => handleItemQuantity(e, item.cartItemId)} className="ml-1">
                  <FaMinus size={15} />
                </button>
                <p className="font-bold text-lg">{quantity}</p>
                <button id="INCREASE" onClick={(e) => handleItemQuantity(e, item.cartItemId)} className="mr-1">
                  <FaPlus size={15} />
                </button>
              </div>
              <button
                className="-mr-1 w-12 h-7 rounded-md bg-gray-300"
                onClick={(e) => {
                  e.preventDefault();
                  setIsModalOpen(true);
                }}
              >
                삭제
              </button>
              <DeleteCartItemModal
                isModalOpen={isModalOpen}
                setIsModalOpen={setIsModalOpen}
                cartItemId={item.cartItemId}
                setItems={setItems}
              />
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default CartItem;

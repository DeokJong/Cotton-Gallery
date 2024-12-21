"use client";

import React, { useEffect, useState } from "react";
import { CartItemType } from "./Cart";
import { FaMinus, FaPlus } from "react-icons/fa";
import { baseUrl } from "@/app/(auth)/_components/SignUp";

type PropsType = {
  item: CartItemType;
};

const CartItem = ({ item }: PropsType) => {
  const [quantity, setQuantity] = useState<number>(item.quantity);
  const [isQuantityChanged, setIsQuantityChanged] = useState<boolean>(false);

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
      console.log(result);

      if (response.ok) {
        alert("장바구니 수량 변경 성공");
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
            <div className="ml-2 mt-2">
              <p className="text-base hidden">판매자</p>
              <p className="text-lg">{item.name}</p>
              <p className="font-bold text-xl">{item.price.toLocaleString()}원</p>
            </div>
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
              <button>삭제</button>
            </div>
          </div>
        </div>
        {/* <div className="w-full h-[45px] pl-2 pr-2 flex justify-between items-center border-t-2">gg</div> */}
      </div>
    </div>
  );
};

export default CartItem;

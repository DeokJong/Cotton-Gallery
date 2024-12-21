"use client";

import { baseUrl } from "@/app/(auth)/_components/SignUp";
import Link from "next/link";
import React, { useEffect, useState } from "react";
import CartItem from "./CartItem";

export type CartItemType = {
  cartItemId: number;
  discountPercent: number;
  name: string;
  itemStatus: string;
  price: number;
  quantity: number;
};

const fetchCartList = async () => {
  try {
    const response = await fetch(`${baseUrl}/api/user/cartItem`, {
      method: "GET",
      credentials: "include",
      headers: {
        Accept: "application/json"
      }
    });

    if (!response.ok) {
      throw new Error("장바구니 목록을 가져오는 데 실패했습니다.");
    }

    const data = await response.json();
    console.log("Response Data:", data); // 데이터 구조 확인
    return data;
  } catch (error) {
    console.error("Error fetching item list:", error);
    return null;
  }
};

const Cart = () => {
  const [items, setItems] = useState<CartItemType[]>([]); // 초기값 빈 배열로 설정

  const fetchItems = async () => {
    const result = await fetchCartList();
    console.log("Fetched Result:", result); // result 확인
    if (result?.data?.cartItem) {
      setItems(result.data.cartItem); // 데이터 경로 수정
    }
  };

  useEffect(() => {
    fetchItems();
  }, []);

  useEffect(() => {
    console.log("Items updated:", items); // items 업데이트 후 확인
  }, [items]);

  return (
    <div className="flex flex-col items-center">
      <ul className="w-[73.75rem] flex flex-col gap-1 items-center">
        {items.map((item, index) => (
          <li key={index} className="mb-[1.25rem]">
            <Link href={`/items/detail/${item.cartItemId}`}>
              <CartItem item={item} />
            </Link>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default Cart;

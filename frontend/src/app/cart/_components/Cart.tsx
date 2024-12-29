"use client";

import { baseUrl } from "@/app/(auth)/_components/SignUp";
import Link from "next/link";
import React, { useEffect, useState } from "react";
import CartItem from "./CartItem";
import { Address } from "react-daum-postcode";

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
  const [items, setItems] = useState<CartItemType[]>([]);
  const [addressId, setAddressId] = useState<number | null>(null);
  const [orderList, setOrderList] = useState<{ cartItemId: number }[]>([]);
  const [primaryAddr, setPrimaryAddr] = useState<Address>();
  const [primaryAddrId, setPrimaryAddrId] = useState<number>();

  const fetchItems = async () => {
    try {
      const result = await fetchCartList();
      console.log("Fetched Result:", result);
      if (result?.data?.cartItem) {
        const fetchedItems = result.data.cartItem;
        setItems(fetchedItems);

        const updatedOrderList = fetchedItems.map((item: any) => ({
          cartItemId: item.cartItemId
        }));
        setOrderList(updatedOrderList);
      }
    } catch (error) {
      console.error("Error fetching items:", error);
    }
  };

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

  const addItemToOrder = (cartItemId: number) => {
    setOrderList((prevItems) => [...prevItems, { cartItemId }]);
  };

  const removeItemFromOrder = (cartItemId: number) => {
    setOrderList((prevItems) => prevItems.filter((item) => item.cartItemId !== cartItemId));
  };

  const orderCartItems = async (addressId: number, orderCartItems: { cartItemId: number }[]) => {
    if (!addressId || !orderCartItems || orderCartItems.length === 0) {
      console.error("Address ID and cart items are required for placing an order.");
      alert("주소와 장바구니 아이템 정보를 입력해주세요.");
      return null;
    }

    try {
      const response = await fetch(`${baseUrl}/api/user/orders/cartItem`, {
        method: "POST",
        credentials: "include",
        headers: {
          Accept: "*/*",
          "Content-Type": "application/json"
        },
        body: JSON.stringify({
          addressId,
          orderCartItems
        })
      });

      if (!response.ok) {
        throw new Error(`Failed to place order: ${response.statusText}`);
      }

      const result = await response.json();
      console.log("Order placed successfully:", result);
      alert("주문을 완료했습니다.");
      return result;
    } catch (error) {
      console.error("Error placing order:", error);
      return null;
    }
  };

  useEffect(() => {
    fetchItems();
    fetchPrimaryAddress();
  }, []);

  useEffect(() => {
    console.log("Items updated:", items);
  }, [items]);

  return (
    <div className="h-screen flex flex-col items-center">
      <h1 className="w-full h-16 border-b-2 text-center border-gray-200 text-[1.75rem] mb-6">장바구니</h1>
      {items.length === 0 ? (
        <div className="flex flex-col justify-center items-center gap-16">
          <p className="text-xl text-gray-500 mt-60">장바구니가 비어있습니다</p>
          <Link href={"/"}>
            <button className="w-40 h-10 rounded bg-gray-200">쇼핑 계속하기</button>
          </Link>
        </div>
      ) : (
        <ul className="w-[73.75rem] flex flex-col gap-1 items-center">
          {items.map((item, index) => (
            <li key={index} className="mb-[1.25rem]">
              <CartItem item={item} setItems={setItems} />
            </li>
          ))}
          <button
            onClick={() => orderCartItems(primaryAddrId as number, orderList)}
            className="w-[48.75rem] h-10 mt-6  mb-5 border-2"
          >
            주문하기
          </button>
        </ul>
      )}
    </div>
  );
};

export default Cart;

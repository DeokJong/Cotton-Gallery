"use client";

import { baseUrl } from "@/app/(auth)/_components/SignUp";
import React, { useEffect, useState } from "react";

type PageInfoType = {
  currentPage: number;
  hasNext: boolean;
  hasPrevious: boolean;
};

type OrderItemType = {
  orderItemId: number;
  itemName: string;
  orderPrice: number;
  count: number;
  discountPercent: number | null;
};

type OrderType = {
  orderId: number;
  orderDate: string;
  deliveryStatus: string;
  pageInfo: PageInfoType;
  orderItems: OrderItemType[];
};

const OrderList = () => {
  const numList = [1, 2, 3, 4, 5];
  const [page, setPage] = useState<number>(1);
  const [orderList, setOrderList] = useState<OrderType[]>([]);

  const fetchOrders = async (page: number) => {
    try {
      const response = await fetch(`${baseUrl}/api/user/orders?page=${page}`, {
        method: "GET",
        credentials: "include",
        headers: {
          Accept: "application/json"
        }
      });

      if (!response.ok) {
        const errorData = await response.json();
        throw new Error(`Failed to fetch orders: ${errorData.message || response.statusText}`);
      }

      const result = await response.json();
      console.log("Fetched orders successfully:", result.data);
      setOrderList(result.data);
      return result.data;
    } catch (error) {
      console.error("Error fetching orders:", error);
      alert("주문 목록을 불러오는 중 오류가 발생했습니다. 다시 시도해주세요.");
      return null;
    }
  };

  useEffect(() => {
    fetchOrders(1);
    // eslint-disable-next-line
  }, []);

  useEffect(() => {
    fetchOrders(page);
    // eslint-disable-next-line
  }, [page]);

  return (
    <div className="h-screen flex flex-col items-center gap-3 mb-3">
      <h1 className="w-full h-16 border-b-2 text-center border-gray-200 text-[1.75rem] mb-6">주문 내역</h1>
      {orderList.length === 0 ? (
        <p className="text-center text-lg font-semibold mt-5">주문 내역이 없습니다</p>
      ) : (
        orderList.map((order) => {
          return (
            <div key={order.orderId} className="flex flex-col w-[48.75rem] h-auto p-3 border-2 rounded-sm">
              <div className="flex justify-between items-center mb-1 px-1">
                <h1 className="font-bold text-xl">{order.orderDate.slice(0, 10)}</h1>
                <button className="w-12 h-7 rounded-md bg-gray-400 text-white">취소</button>
              </div>
              {order.orderItems.map((item) => {
                return (
                  <div key={item.orderItemId} className="mt-2 border-t-2 px-2">
                    <h3 className="font-semibold text-lg mb-1 mt-3">{item.itemName}</h3>
                    <p className="text-base mb-1">수량: {item.count}</p>
                    <h3 className="font-semibold text-lg">{item.orderPrice.toLocaleString()}원</h3>
                  </div>
                );
              })}
            </div>
          );
        })
      )}

      <div className="flex gap-10 h-40">
        {numList.map((num) => {
          return (
            <button onClick={() => setPage(num)} className="text-lg mb-10 mt-5">
              {num}
            </button>
          );
        })}
      </div>
    </div>
  );
};

export default OrderList;

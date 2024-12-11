"use client";

import React, { useEffect, useState } from "react";
import GoodsCard, { Item } from "./GoodsCard";
import usePageStore from "@/store/pageStore";
import Link from "next/link";

const categoryList = ["카테고리", "신상품", "베스트", "단독특가", "이벤트/특가"];
//const cardList = [1, 2, 3, 4, 5, 6, 7, 8, 9];

const getItemList = async (pageNumber: number) => {
  try {
    const response = await fetch(`http://localhost:8080/api/items?page=${pageNumber}&itemSort=CREATED_DATE`, {
      method: "GET",
      credentials: "include",
      headers: {
        Accept: "application/json"
      }
    });

    if (!response.ok) {
      throw new Error("아이템 목록을 가져오는 데 실패했습니다.");
    }

    const data = await response.json();
    console.log(data);
    return data;
  } catch (error) {
    console.error("Error fetching item list:", error);
    return null;
  }
};

const Home = () => {
  // Todo : 쿠키 가져와서 없는 경우에는 로그인 버튼 / 있으면 로그아웃 버튼 렌더링
  const { pageNumber } = usePageStore();
  const [items, setItems] = useState<Item[]>([]);

  const fetchItems = async (pageNumber: number) => {
    const result = await getItemList(pageNumber);
    if (result) {
      console.log("아이템 리스트:", result.data.items);
      setItems(result.data.items);
    }
  };

  useEffect(() => {
    fetchItems(pageNumber);
    //eslint-disable-next-line
  }, [pageNumber]);

  return (
    <div className="flex flex-col items-center">
      <ul className="w-full flex gap-24 border-gray-400 border-b-2 justify-center mb-10">
        {categoryList.map((category) => (
          <li key={category} className="mb-2">
            {category}
          </li>
        ))}
      </ul>

      <ul className="w-[73.75rem] flex flex-wrap justify-between">
        {/* {cardList.map((card) => (
          <li key={card} className="mb-[1.25rem]">
            <GoodsCard />
          </li>
        ))} */}
        {items.map((item, index) => (
          <li key={index} className="mb-[1.25rem]">
            {/* GoodsCard에 item 데이터를 props로 전달 */}
            <Link href={`/items/detail/${item.itemId}`}>
              <GoodsCard item={item} />
            </Link>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default Home;

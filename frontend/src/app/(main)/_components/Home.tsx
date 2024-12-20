"use client";

import React, { useEffect, useState } from "react";
import GoodsCard, { Item } from "./GoodsCard";
import usePageStore from "@/store/pageStore";
import Link from "next/link";
import { baseUrl } from "@/app/(auth)/_components/SignUp";
import useCategoryStore from "@/store/categoryStore";

const getItemList = async (pageNumber: number, category: string) => {
  if (category === "LIKED_BY_ME") {
    try {
      const response = await fetch(`${baseUrl}/api/public/items?page=${pageNumber}&itemSort=CREATED_DATE`, {
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
  }
  try {
    const response = await fetch(`${baseUrl}/api/public/items?page=${pageNumber}&itemSort=${category}`, {
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
  const { category } = useCategoryStore();
  const { pageNumber } = usePageStore();
  const [items, setItems] = useState<Item[]>([]);

  const fetchItems = async (pageNumber: number, category: string) => {
    // TODO: 페이지네이션
    const result = await getItemList(pageNumber, category);
    if (result && result.data.items) {
      const filteredItems =
        category === "LIKED_BY_ME" ? result.data.items.filter((item: Item) => item.likedByMe) : result.data.items;
      setItems(filteredItems);
    }
  };

  useEffect(() => {
    fetchItems(pageNumber, category);
    //eslint-disable-next-line
  }, [pageNumber, category]);

  return (
    <div className="flex flex-col items-center">
      <ul className="w-[73.75rem] flex flex-wrap gap-5 items-center">
        {items.map((item, index) => (
          <li key={index} className="mb-[1.25rem]">
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

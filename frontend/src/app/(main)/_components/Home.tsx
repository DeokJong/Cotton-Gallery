"use client";

import React from "react";
import GoodsCard from "./GoodsCard";

const categoryList = ["카테고리", "신상품", "베스트", "단독특가", "이벤트/특가"];
const cardList = [1, 2, 3, 4, 5, 6, 7, 8, 9];

const Home = () => {
  // Todo : 쿠키 가져와서 없는 경우에는 로그인 버튼 / 있으면 로그아웃 버튼 렌더링

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
        {cardList.map((card) => (
          <li key={card} className="mb-[1.25rem]">
            <GoodsCard />
          </li>
        ))}
      </ul>
    </div>
  );
};

export default Home;

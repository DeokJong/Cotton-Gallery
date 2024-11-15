"use client";

import React from "react";
import GoodsCard from "./GoodsCard";

const categoryList = ["카테고리", "신상품", "베스트", "단독특가", "이벤트/특가"];
const cardList = [1, 2, 3, 4, 5, 6, 7, 8, 9];

const Home = () => {
  // Todo : 로그아웃 버튼 메인헤더로 옮기기
  // Todo : 쿠키 가져와서 없는 경우에는 로그인 버튼 / 있으면 로그아웃 버튼 렌더링

  const handleLogoutBtn = async () => {
    const response = await fetch("http://localhost:8080/api/logout", {
      method: "POST",
      credentials: "include",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify({})
    });

    const result = await response;
    console.log(result);
  };

  return (
    <div className="flex flex-col items-center">
      {/* <button className="w-20 bg-slate-200" onClick={handleLogoutBtn}>
        로그아웃
      </button> */}
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

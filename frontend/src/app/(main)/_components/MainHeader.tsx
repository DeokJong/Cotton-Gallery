"use client";

import Logo from "@/components/Logo";
import { API_URL } from "@/constants";
import { useAuthStore } from "@/store/authStore";
import Link from "next/link";
import React from "react";

const categoryList = ["카테고리", "신상품", "베스트", "단독특가", "이벤트/특가"];

const MainHeader = () => {
  const { name, setName } = useAuthStore();

  const handleLogoutBtn = async () => {
    const response = await fetch(`${API_URL}/api/auth/logout`, {
      method: "POST",
      credentials: "include",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify({})
    });

    const result = await response;
    console.log(result);
    if (result.status === 204) {
      alert("로그아웃 되었습니다");
      setName("");
    }
  };

  return (
    <>
      <div className="w-full mt-[3.75rem] mb-[3.75rem] flex justify-center gap-[22.5rem]">
        <p>찜 리스트</p>
        <Link href={"/"}>
          <Logo />
        </Link>
        {/* TODO: 로그인 여부 따라 다르게 렌더링 */}
        <div className="flex gap-3">
          <button>{name} 님</button>
          <button onClick={handleLogoutBtn}>로그아웃</button>
        </div>
      </div>
      <ul className="w-full flex gap-24 border-gray-400 border-b-2 justify-center mb-10">
        {categoryList.map((category) => (
          <li key={category} className="mb-2">
            {category}
          </li>
        ))}
      </ul>
    </>
  );
};

export default MainHeader;

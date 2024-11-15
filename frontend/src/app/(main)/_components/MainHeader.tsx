"use client";

import Logo from "@/components/Logo";
import { useAuthStore } from "@/store/authStore";
import React from "react";

const MainHeader = () => {
  const { name } = useAuthStore();

  return (
    <div className="w-full mt-[3.75rem] mb-[3.75rem] flex justify-center gap-[22.5rem]">
      <p>찜 리스트</p>
      <Logo />
      {/* TODO: 로그인 여부 따라 다르게 렌더링 */}
      <div className="flex gap-3">
        <button>{name} 님</button>
        <button>로그아웃</button>
      </div>
    </div>
  );
};

export default MainHeader;

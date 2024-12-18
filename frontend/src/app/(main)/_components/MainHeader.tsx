"use client";

import Logo from "@/components/Logo";
import { useAuthStore } from "@/store/authStore";
import useIsLoggedinStore from "@/store/isLoggedinStore";
import Link from "next/link";
import { useRouter } from "next/navigation";
import React, { useEffect, useState } from "react";

const categoryList = ["카테고리", "신상품", "베스트", "단독특가", "이벤트/특가"];

const MainHeader = () => {
  const { isLoggedin, setIsLoggedin } = useIsLoggedinStore();
  const { name, setName } = useAuthStore();
  const router = useRouter();

  const handleLogoutBtn = async () => {
    const response = await fetch("http://localhost:8080/api/auth/logout", {
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
      setIsLoggedin(false);
    }
  };

  useEffect(() => {
    if (!isLoggedin) {
      setIsLoggedin(false);
    }
    //eslint-disable-next-line
  }, []);

  return (
    <>
      <div className="w-full mt-[3.75rem] mb-[3.75rem] flex justify-center items-center gap-[22.5rem]">
        <div className="flex gap-3">
          <p>찜 리스트</p>
          {name === "Admin" && (
            <Link href={"/items/add"}>
              <p>상품 등록</p>
            </Link>
          )}
        </div>
        <Link href={"/"}>
          <Logo />
        </Link>
        <div className="flex gap-3">
          {!isLoggedin ? (
            <>
              <Link href={"/login"}>
                <button>로그인</button>
              </Link>
              <Link href={"/join"}>
                <button>회원가입</button>
              </Link>
            </>
          ) : (
            <>
              <button>{name} 님</button>
              <button onClick={handleLogoutBtn}>로그아웃</button>
            </>
          )}
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

"use client";

import { useAuthStore } from "@/store/authStore";
import { FaChevronRight } from "react-icons/fa6";
import React from "react";
import Link from "next/link";

const MyPage = () => {
  const { name } = useAuthStore();
  console.log(name);

  return (
    <div className="h-screen flex flex-col items-center">
      <h1 className="w-full h-16 border-b-2 text-center border-gray-200 text-[1.75rem] mb-6">마이페이지</h1>
      <h2 className="w-[36.25rem] h-[3.75rem] border-gray-200 text-[1.75rem]">{name}님</h2>
      <Link
        href={"/my-page/edit/email"}
        className="w-[36.25rem] h-[3.75rem] flex justify-between items-center text-[1.25rem] mb-2 p-3 cursor-pointer hover:bg-gray-100 duration-200 rounded-sm"
      >
        <button>이메일 변경</button>
        <FaChevronRight color="gray" />
      </Link>
      <Link
        href={"/my-page/edit/password"}
        className="w-[36.25rem] h-[3.75rem] flex justify-between items-center text-[1.25rem] mb-2 p-3 cursor-pointer hover:bg-gray-100 duration-200 rounded-sm"
      >
        <button>비밀번호 변경</button>
        <FaChevronRight color="gray" />
      </Link>
      <Link
        href={"/my-page/order"}
        className="w-[36.25rem] h-[3.75rem] flex justify-between items-center text-[1.25rem] mb-2 p-3 cursor-pointer hover:bg-gray-100 duration-200 rounded-sm"
      >
        <button>주문 내역</button>
        <FaChevronRight color="gray" />
      </Link>
    </div>
  );
};

export default MyPage;

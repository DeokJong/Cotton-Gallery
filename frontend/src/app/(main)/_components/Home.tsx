"use client";

import React from "react";

const Home = () => {
  // Todo : 메인 페이지 와이어프레임
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
      메인페이지
      <button className="w-20 bg-slate-200" onClick={handleLogoutBtn}>
        로그아웃
      </button>
    </div>
  );
};

export default Home;

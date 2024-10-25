"use client";

import React from "react";
import InputBox from "./InputBox";
import { useAuthStore } from "@/store/authStore";

const Login = () => {
  const { username, password, error, setUsername, setPassword, setError } = useAuthStore();

  const handleUsernameChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setUsername(e.target.value);
    // Todo : 에러메세지 수정
    setError({ ...error, username: "아이디에러메세지" });
  };
  const handlePasswordChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setPassword(e.target.value);
  };

  const handleSubmitSignUpForm = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    const response = await fetch("/api/login", {
      method: "POST",
      headers: {
        "Content-Type": "application/json" // 헤더 추가
      },
      body: JSON.stringify({
        username,
        password
      })
    });

    const result = await response.json();
    console.log(result);
    //console.log(typeof phoneNumber);
    //console.log(name, username, password, phoneNumber, passwordConfirm, email, zipcode, street, detail);
  };

  return (
    <div className="flex flex-col items-center">
      <h1 className="text-[1.75rem] mb-[3.438rem]">로그인</h1>
      <form onSubmit={handleSubmitSignUpForm} className="flex flex-col gap-[1.875rem]">
        <InputBox
          id="username"
          text="아이디"
          placeholder="아이디를 입력해주세요"
          type="text"
          value={username}
          onChange={handleUsernameChange}
          error={error}
        />
        <InputBox
          id="password"
          text="비밀번호"
          placeholder="비밀번호를 입력해주세요"
          type="password"
          value={password}
          onChange={handlePasswordChange}
          error={error}
        />
      </form>
    </div>
  );
};

export default Login;

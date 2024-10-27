"use client";

import React from "react";
import InputBox from "./InputBox";
import { useAuthStore } from "@/store/authStore";
import SubmitBtn from "./SubmitBtn";
import Link from "next/link";
import { useRouter } from "next/navigation";

const Login = () => {
  const router = useRouter();
  const { username, password, error, setUsername, setPassword, setError } = useAuthStore();

  const handleUsernameChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setUsername(e.target.value);
    // Todo : 에러메세지 수정
    setError({ ...error, username: "아이디에러메세지" });
  };
  const handlePasswordChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setPassword(e.target.value);
  };

  const handleSubmitLoginForm = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    const response = await fetch("http://localhost:8080/api/login", {
      method: "POST",
      credentials: "include",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify({
        username,
        password
      })
    });
    const result = await response.json();
    console.log(result);
    if (result.status === 200) {
      router.push("/");
    }
    //console.log(typeof phoneNumber);
    //console.log(name, username, password, phoneNumber, passwordConfirm, email, zipcode, street, detail);
  };

  return (
    <div className="flex flex-col items-center">
      <h1 className="text-[1.75rem] mb-[3.438rem]">로그인</h1>
      <form onSubmit={handleSubmitLoginForm} className="flex flex-col gap-[1.875rem]">
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
        <div className="flex justify-between ml-2 mr-2 text-gray-500 ">
          <p>아직 회원이 아니신가요?</p>
          <Link href={"/join"}>
            <p className="text-gray-500 font-semibold">회원가입</p>
          </Link>
        </div>
        <SubmitBtn text="login" />
      </form>
    </div>
  );
};

export default Login;

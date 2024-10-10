"use client";

import InputBox from "./InputBox";
import { useAuthStore } from "@/store/authStore";
import SubmitBtn from "./SubmitBtn";
import { useState } from "react";

const SignUp = () => {
  const [isModalOpen, setIsModalOpen] = useState<boolean>(false);
  const {
    username,
    password,
    passwordConfirm,
    name,
    phoneNumber,
    email,
    error,
    setUsername,
    setPassword,
    setPasswordConfirm,
    setName,
    setPhoneNumber,
    setEmail,
    setError
  } = useAuthStore();

  const handleUsernameChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setUsername(e.target.value);
    // Todo : 에러메세지 수정
    setError({ ...error, username: "아이디에러메세지" });
  };
  const handlePasswordChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setPassword(e.target.value);
  };
  const handlePasswordConfirmChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setPasswordConfirm(e.target.value);
  };
  const handleNameChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setName(e.target.value);
  };
  const handlePhoneNumberChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setPhoneNumber(e.target.value);
  };
  const handleEmailChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setEmail(e.target.value);
  };

  const handleAddressBtnClick = () => {
    setIsModalOpen(true);
  };

  return (
    <div className="flex flex-col items-center">
      <h1 className="text-[1.75rem] mb-[3.438rem]">회원가입</h1>
      <form className="flex flex-col gap-[1.875rem]">
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
        <InputBox
          id="passwordConfirm"
          text="비밀번호 확인"
          placeholder="비밀번호를 한 번 더 입력해주세요"
          type="password"
          value={passwordConfirm}
          onChange={handlePasswordConfirmChange}
          error={error}
        />
        <InputBox
          id="name"
          text="이름"
          placeholder="이름을 입력해주세요."
          type="text"
          value={name}
          onChange={handleNameChange}
          error={error}
        />
        <InputBox
          id="email"
          text="이메일"
          placeholder="welcome@example.com"
          type="email"
          value={email}
          onChange={handleEmailChange}
          error={error}
        />
        <InputBox
          id="phoneNumber"
          text="휴대폰 번호"
          placeholder="01012345678"
          type="text"
          value={phoneNumber}
          onChange={handlePhoneNumberChange}
          error={error}
        />
        <div className="text-xl">
          <label htmlFor="address" className="ml-3">
            주소
          </label>
          <div className="flex justify-between mt-2">
            <input
              id="address"
              type="text"
              alt="주소"
              placeholder="주소"
              className="w-[26.25rem] h-[3.75rem] indent-5 rounded-[35px] bg-gray-200"
            />
            <button
              type="button"
              onClick={handleAddressBtnClick}
              className="w-[9.375rem] h-[3.438rem]  rounded-lg bg-gray-300"
            >
              우편번호 검색
            </button>
          </div>
          <input
            id="addressDetail"
            type="text"
            alt="상세 주소"
            placeholder="상세 주소"
            className="w-[36.25rem] h-[3.75rem] mt-2 indent-5 rounded-[35px] bg-gray-200"
          />
        </div>
        <div className="mt-[1.375rem] mb-[3.375rem] flex flex-col gap-5">
          <SubmitBtn text="회원가입" />
          <SubmitBtn text="로그인" />
        </div>
      </form>
    </div>
  );
};

export default SignUp;

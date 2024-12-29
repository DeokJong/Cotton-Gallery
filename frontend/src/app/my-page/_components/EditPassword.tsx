"use client";

import InputBox from "@/app/(auth)/_components/InputBox";
import { useAuthStore } from "@/store/authStore";
import React from "react";

const EditPassword = () => {
  const { password, passwordConfirm, setPassword, setPasswordConfirm, error, setError } = useAuthStore();

  const handlePasswordChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setPassword(e.target.value);
  };
  const handlePasswordConfirmChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setPasswordConfirm(e.target.value);
  };

  return (
    <div className="h-screen flex flex-col items-center">
      <h1 className="w-full h-16 border-b-2 text-center border-gray-200 text-[1.75rem] mb-6">비밀번호 변경</h1>
      <InputBox
        id="password"
        text="비밀번호"
        placeholder="비밀번호를 입력해주세요"
        type="password"
        value={password}
        onChange={handlePasswordChange}
        error={error.password}
      />
      <InputBox
        id="passwordConfirm"
        text="비밀번호 확인"
        placeholder="비밀번호를 한 번 더 입력해주세요"
        type="password"
        value={passwordConfirm}
        onChange={handlePasswordConfirmChange}
        error={error.passwordConfirm}
      />
    </div>
  );
};

export default EditPassword;

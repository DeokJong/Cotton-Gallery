"use client";

import InputBox from "@/app/(auth)/_components/InputBox";
import { baseUrl } from "@/app/(auth)/_components/SignUp";
import { useAuthStore } from "@/store/authStore";
import { useRouter } from "next/navigation";
import React, { useEffect } from "react";

const EditPassword = () => {
  const router = useRouter();
  const { password, passwordConfirm, setPassword, setPasswordConfirm, error, setError } = useAuthStore();

  const handlePasswordChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setPassword(e.target.value);
  };
  const handlePasswordConfirmChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setPasswordConfirm(e.target.value);
  };

  const handleEditPassword = async () => {
    try {
      if (password !== passwordConfirm) {
        setError("passwordConfirm", "비밀번호가 일치하지 않습니다.");
        return;
      }

      const response = await fetch(`${baseUrl}/api/user/accounts/change-password`, {
        method: "PATCH",
        credentials: "include",
        headers: {
          Accept: "application/json",
          "Content-Type": "application/json"
        },
        body: JSON.stringify({ password, confirmPassword: passwordConfirm })
      });

      if (!response.ok) {
        const errorData = await response.json();
        throw new Error(`Failed to change password: ${errorData.message || response.statusText}`);
      }

      const data = await response.json();
      console.log("Password changed successfully:", data);
      alert("비밀번호가 성공적으로 변경되었습니다.");
      router.push("/my-page");
      return data;
    } catch (error) {
      console.error("Error changing password:", error);
      alert("비밀번호 변경 중 오류가 발생했습니다. 다시 시도해주세요.");
      return null;
    }
  };

  useEffect(() => {
    setError("passwordConfirm", "");
    // eslint-disable-next-line
  }, []);

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
      <button
        onClick={handleEditPassword}
        className="mt-3 w-[36.25rem] h-[3.438rem] flex justify-center items-center text-xl rounded-[0.625rem] bg-gray-200 hover:bg-gray-300 duration-200 active:bg-gray-400"
      >
        변경하기
      </button>
    </div>
  );
};

export default EditPassword;

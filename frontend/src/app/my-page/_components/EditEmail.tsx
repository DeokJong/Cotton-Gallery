"use client";

import InputBox from "@/app/(auth)/_components/InputBox";
import { baseUrl } from "@/app/(auth)/_components/SignUp";
import { useAuthStore } from "@/store/authStore";
import { useRouter } from "next/navigation";
import React, { useEffect } from "react";

const EditEmail = () => {
  const router = useRouter();
  const { email, error, setEmail, setError } = useAuthStore();

  const handleEmailChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setEmail(e.target.value);
  };

  const handleEditEmail = async () => {
    try {
      const response = await fetch(`${baseUrl}/api/user/accounts/change-email`, {
        method: "PATCH",
        credentials: "include",
        headers: {
          Accept: "application/json",
          "Content-Type": "application/json"
        },
        body: JSON.stringify({ email })
      });

      if (!response.ok) {
        const errorData = await response.json();
        throw new Error(`Failed to change email: ${errorData.message || response.statusText}`);
      }

      const data = await response.json();
      console.log("Email changed successfully:", data);
      alert("이메일이 성공적으로 변경되었습니다.");
      router.push("/my-page");
      return data;
    } catch (error) {
      console.error("Error changing email:", error);
      alert("이메일 변경 중 오류가 발생했습니다. 다시 시도해주세요.");
      return null;
    }
  };

  useEffect(() => {
    setError("email", "");
    // eslint-disable-next-line
  }, []);

  return (
    <div className="h-screen flex flex-col items-center">
      <h1 className="w-full h-16 border-b-2 text-center border-gray-200 text-[1.75rem] mb-6">이메일 변경</h1>
      <InputBox
        id="email"
        text="이메일"
        placeholder="welcome@example.com"
        type="email"
        value={email}
        onChange={handleEmailChange}
        error={error.email}
      />
      <button
        onClick={handleEditEmail}
        className="mt-3 w-[36.25rem] h-[3.438rem] flex justify-center items-center text-xl rounded-[0.625rem] bg-gray-200 hover:bg-gray-300 duration-200 active:bg-gray-400"
      >
        변경하기
      </button>
    </div>
  );
};

export default EditEmail;

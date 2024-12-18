"use client";

import InputBox from "./InputBox";
import { useAuthStore } from "@/store/authStore";
import SubmitBtn from "./SubmitBtn";
import { useState } from "react";
import AddressModal from "./AddressModal";
import Link from "next/link";

const SignUp = () => {
  const [isModalOpen, setIsModalOpen] = useState<boolean>(false);
  const {
    username,
    password,
    passwordConfirm,
    name,
    phoneNumber,
    email,
    zipcode,
    street,
    detail,
    error,
    setUsername,
    setPassword,
    setPasswordConfirm,
    setName,
    setPhoneNumber,
    setEmail,
    setZipcode,
    setStreet,
    setDetail,
    setError
  } = useAuthStore();

  const handleUsernameChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setUsername(e.target.value);
    // Todo : 에러메세지 수정
    setError("username", "");
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
  const handleDetailChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setDetail(e.target.value);
  };

  const handleSubmitSignUpForm = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    // Todo : 요청 보내기 전 휴대폰번호 상태값 정규식 검사
    // Todo : 요청 보내기 전 비밀번호, 비밀번호 확인 값 같은지 검사

    const response = await fetch("/api/sign-up", {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify({
        name,
        username,
        password,
        confirmPassword: passwordConfirm,
        email,
        phoneNumber,
        zipcode,
        street,
        detail
      })
    });

    const result = await response.json();
    console.log(result);

    if (result.data) {
      const blank = result.data.filter((error: Record<string, string>) => error.code === "NotBlank");
      blank.forEach((error: Record<string, string>) => {
        switch (error.field) {
          //console.log("Current error state:", useAuthStore.getState().error);
          case "username":
            setError("username", "아이디를 10자 이내로 입력해주세요");
            break;
          case "password":
            setError("password", "비밀번호를 입력해주세요");
            break;
          case "confirmPassword":
            setError("passwordConfirm", "비밀번호를 한 번 더 입력해주세요");
            break;
          case "name":
            setError("name", "이름을 입력해주세요");
            break;
          case "email":
            setError("email", "이메일을 입력해주세요.");
            break;
          case "phoneNumber":
            setError("phoneNumber", "휴대폰 번호를 입력해주세요");
            break;
          case "street":
          case "detail":
            setError("street", "주소를 입력해주세요");
            break;
          default:
            break;
        }
      });

      const minLength = result.data.filter((error: Record<string, string>) => error.code === "Size");
      minLength.forEach((error: Record<string, string>) => {
        switch (error.field) {
          case "password":
            setError("password", "비밀번호를 8자~15자 이내로 입력해주세요");
            break;
          case "confirmPassword":
            setError("passwordConfirm", "비밀번호를 8자~15자 이내로 입력해주세요");
            break;
          default:
            break;
        }
      });
    }
  };

  return (
    <div className="flex flex-col items-center">
      <h1 className="text-[1.75rem] mb-[3.438rem]">회원가입</h1>
      <form onSubmit={handleSubmitSignUpForm} className="flex flex-col gap-[1.875rem]">
        <InputBox
          id="username"
          text="아이디"
          placeholder="아이디를 입력해주세요"
          type="text"
          value={username}
          onChange={handleUsernameChange}
          error={error.username}
        />
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
        <InputBox
          id="name"
          text="이름"
          placeholder="이름을 입력해주세요."
          type="text"
          value={name}
          onChange={handleNameChange}
          error={error.name}
        />
        <InputBox
          id="email"
          text="이메일"
          placeholder="welcome@example.com"
          type="email"
          value={email}
          onChange={handleEmailChange}
          error={error.email}
        />
        <InputBox
          id="phoneNumber"
          text="휴대폰 번호"
          placeholder="01012345678"
          type="text"
          value={phoneNumber}
          onChange={handlePhoneNumberChange}
          error={error.phoneNumber}
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
              value={street}
              className="w-[26.25rem] h-[3.75rem] indent-5 rounded-[35px] bg-gray-200 focus:outline-none"
              readOnly
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
            value={detail}
            onChange={handleDetailChange}
            className="w-[36.25rem] h-[3.75rem] mt-2 indent-5 rounded-[35px] bg-gray-200"
          />
          <p className="pt-1 indent-3 text-sm text-red-500">{error.street}</p>
          <AddressModal
            isModalOpen={isModalOpen}
            setIsModalOpen={setIsModalOpen}
            setZipcode={setZipcode}
            setStreet={setStreet}
          />
        </div>
        <div className="mt-[1.375rem] mb-[3.375rem] flex flex-col gap-5">
          <SubmitBtn text="회원가입" />
          <Link href={"/login"}>
            <SubmitBtn text="로그인" />
          </Link>
        </div>
      </form>
    </div>
  );
};

export default SignUp;

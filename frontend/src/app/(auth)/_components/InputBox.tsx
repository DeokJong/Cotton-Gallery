import React from "react";

type PropsType = {
  id: "username" | "password" | "passwordConfirm" | "name" | "phoneNumber" | "email";
  type: string;
  placeholder: string;
  value: string;
  onChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
  text: string;
  error: Record<string, string>;
  //ishide?: boolean;
  //setHide?: Dispatch<SetStateAction<boolean>>;
};

const InputBox = ({ id, type, placeholder, value, onChange, text, error }: PropsType) => {
  // TODO: 비밀번호 눈 아이콘 로직 추가
  return (
    <div className="flex flex-col text-xl">
      <label htmlFor="id" className="indent-3 mb-2">
        {text}
      </label>
      <input
        id={id}
        type={type}
        value={value}
        onChange={onChange}
        placeholder={placeholder}
        className="w-[36.25rem] h-[3.75rem] indent-5 rounded-[35px] bg-gray-200"
      />
      {/* 아이콘 위치 */}
      <p>{error[id]}</p>
    </div>
  );
};

export default InputBox;

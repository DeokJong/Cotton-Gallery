"use client";

import { useEffect, useState } from "react";

type PropsType = {
  text: string;
};

const SubmitBtn = ({ text }: PropsType) => {
  const [isLoginBtn, setIsLoginBtn] = useState<boolean>(false);

  useEffect(() => {
    if (text === "로그인") {
      setIsLoginBtn(true);
    }
  }, []);
  console.log(isLoginBtn);

  return (
    <div>
      <button
        type="submit"
        className={`w-[36.25rem] h-[3.438rem] flex justify-center items-center text-xl rounded-[0.625rem] ${isLoginBtn ? `bg-white` : `bg-gray-200`} ${isLoginBtn && `border-2`} hover:bg-gray-300 duration-200 active:bg-gray-400`}
      >
        {text}
      </button>
    </div>
  );
};

export default SubmitBtn;

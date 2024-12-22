"use client";
import { API_URL } from "@/constants";
import { useItemStore } from "@/store/itemStore";
import React from "react";

const AddItem = () => {
  const { name, price, stockQuantity, content, setName, setPrice, setStockQuantity, setContent } = useItemStore();
  const handleNameChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setName(e.target.value);
  };
  const handlePriceChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setPrice(+e.target.value);
  };
  const handleStockQuantityChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setStockQuantity(+e.target.value);
  };
  const handleContentChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    // const file = e.target.files?.[0];
    // if (file) {
    //   const reader = new FileReader();
    //   reader.onload = () => {
    //     const base64String = reader.result?.toString() || "";
    //     setContent(base64String);
    //   };
    //   reader.readAsDataURL(file);
    // }
    setContent(e.target.value);
  };

  const handleSubmitItem = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    const response = await fetch(`${API_URL}/api/items`, {
      method: "POST",
      credentials: "include",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify({
        name,
        price,
        stockQuantity,
        content
      })
    });

    const result = await response.json();
    console.log(result);
    // TODO: 스윗알럿으로 변경
    if (result.status === 201) {
      alert("상품 생성 성공");
    } else {
      alert(`상품 생성 실패: ${result.message}`);
    }
  };

  return (
    <form className="flex flex-col text-xl" onSubmit={handleSubmitItem}>
      <label htmlFor="name" className="indent-3 mb-2">
        상품 이름
      </label>
      <input
        id="name"
        type="text"
        value={name}
        onChange={handleNameChange}
        placeholder="상품 이름"
        className="w-[36.25rem] h-[3.75rem] indent-5 rounded-[35px] bg-gray-200"
      />
      <label htmlFor="price" className="indent-3 mb-2">
        상품 가격
      </label>
      <input
        id="price"
        type="number"
        value={price}
        onChange={handlePriceChange}
        placeholder="상품 가격"
        className="w-[36.25rem] h-[3.75rem] indent-5 rounded-[35px] bg-gray-200"
      />
      <label htmlFor="stockQuantity" className="indent-3 mb-2">
        상품 수량
      </label>
      <input
        id="stockQuantity"
        type="number"
        value={stockQuantity}
        onChange={handleStockQuantityChange}
        placeholder="상품 수량"
        className="w-[36.25rem] h-[3.75rem] indent-5 rounded-[35px] bg-gray-200"
      />
      <label htmlFor="content" className="indent-3 mb-2">
        상품 상세 내용
      </label>
      <input
        id="content"
        type="text"
        onChange={handleContentChange}
        placeholder="상품 내용"
        className="w-[36.25rem] h-[3.75rem] indent-5 rounded-[35px] bg-gray-200"
      />
      <button>등록</button>
    </form>
  );
};

export default AddItem;

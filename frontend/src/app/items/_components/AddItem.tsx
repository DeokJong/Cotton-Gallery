"use client";
import { baseUrl } from "@/app/(auth)/_components/SignUp";
import { useItemStore } from "@/store/itemStore";
import React, { useEffect } from "react";

const AddItem = () => {
  const {
    name,
    price,
    stockQuantity,
    itemImage,
    itemInfoImage,
    setName,
    setPrice,
    setStockQuantity,
    setItemImage,
    setItemInfoImage
  } = useItemStore();
  const handleNameChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setName(e.target.value);
  };
  const handlePriceChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setPrice(+e.target.value);
  };
  const handleStockQuantityChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setStockQuantity(+e.target.value);
  };
  const handleItemImageChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const file = e.target.files?.[0];
    if (file) {
      setItemImage(file);
    }
  };

  const handleItemInfoImageChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const file = e.target.files?.[0];
    if (file) {
      setItemInfoImage(file);
    }
  };

  const handleSubmitItem = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    const formData = new FormData();
    formData.append("name", name);
    formData.append("price", price.toString());
    formData.append("stockQuantity", stockQuantity.toString());
    if (itemImage) formData.append("itemImage", itemImage);
    if (itemInfoImage) formData.append("itemInfoImage", itemInfoImage);

    try {
      const response = await fetch(`${baseUrl}/api/admin/items`, {
        method: "POST",
        credentials: "include",
        body: formData
      });

      const result = await response.json();
      console.log(result);

      if (response.ok) {
        alert("상품 생성 성공");
      } else {
        alert(`상품 생성 실패: ${result.message}`);
      }
    } catch (error) {
      console.error("요청 중 오류 발생:", error);
      alert("상품 생성 중 오류가 발생했습니다.");
    }
  };

  useEffect(() => {
    setName("");
    setPrice(0);
    setStockQuantity(0);
  }, []);

  return (
    <form className="flex flex-col text-xl" onSubmit={handleSubmitItem}>
      <h1 className="text-[1.75rem] mb-6">상품 정보 수정</h1>
      <label htmlFor="name" className="indent-3 mt-2 mb-2">
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
      <label htmlFor="price" className="indent-3 mt-2 mb-2">
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
      <label htmlFor="stockQuantity" className="indent-3 mt-2 mb-2">
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
      <label htmlFor="content" className="indent-3 mt-2 mb-2">
        상품 대표 이미지
      </label>
      <input
        id="content"
        type="file"
        onChange={handleItemImageChange}
        className="w-[36.25rem] h-[3.75rem] indent-5 rounded-[35px]"
      />
      <label htmlFor="content" className="indent-3 mt-2 mb-2">
        상품 설명 이미지
      </label>
      <input
        id="content"
        type="file"
        onChange={handleItemInfoImageChange}
        className="w-[36.25rem] h-[3.75rem] indent-5 rounded-[35px]"
      />
      <button>등록</button>
    </form>
  );
};

export default AddItem;

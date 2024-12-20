"use client";

import { baseUrl } from "@/app/(auth)/_components/SignUp";
import { Item } from "@/app/(main)/_components/GoodsCard";
import { useItemStore } from "@/store/itemStore";
import React, { useEffect, useState } from "react";
import { getItem } from "./ItemDetail";

type ItemEditPropsType = {
  itemId: number;
};

const EditItem = ({ itemId }: ItemEditPropsType) => {
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
  const [item, setItem] = useState<Item>();

  const fetchItem = async (itemId: number) => {
    const result = await getItem(itemId);
    if (result) {
      console.log("아이템", result.data);
      setItem(result.data);
    }
  };

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

  const handleEditItemInfo = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
  };

  const handleEditItemImage = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    const formData = new FormData();
    if (itemImage) formData.append("itemImage", itemImage);

    try {
      const response = await fetch(`${baseUrl}/api/admin/items/${itemId}?imageType=ITEM_IMAGE`, {
        method: "PUT",
        credentials: "include",
        body: formData
      });

      const result = await response.json();
      console.log(result);

      if (response.ok) {
        alert("상품 이미지 변경 성공");
      } else {
        alert(`상품 이미지 변경 실패: ${result.message}`);
      }
    } catch (error) {
      console.error("요청 중 오류 발생:", error);
      alert("상품 이미지 변경중 오류가 발생했습니다.");
    }
  };

  const handleEditItemInfoImage = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    const formData = new FormData();
    if (itemInfoImage) formData.append("itemImage", itemInfoImage);

    try {
      const response = await fetch(`${baseUrl}/api/admin/items/${itemId}?imageType=ITEM_INFO_IMAGE`, {
        method: "PUT",
        credentials: "include",
        body: formData
      });

      const result = await response.json();
      console.log(result);

      if (response.ok) {
        alert("상품 상세 이미지 변경 성공");
      } else {
        alert(`상품 상세 이미지 변경 실패: ${result.message}`);
      }
    } catch (error) {
      console.error("요청 중 오류 발생:", error);
      alert("상품 상세 이미지 변경중 오류가 발생했습니다.");
    }
  };

  useEffect(() => {
    fetchItem(itemId);
    //eslint-disable-next-line
  }, []);

  return (
    <div className="flex flex-col justify-center">
      <form className="flex flex-col text-xl" onSubmit={handleEditItemInfo}>
        <div className="w-[36.25rem] flex justify-between items-center mb-[2rem]">
          <h1 className="text-[1.75rem] ">상품 정보 수정</h1>
          <button>수정하기</button>
        </div>
        <label htmlFor="name" className="indent-3 mb-2">
          상품 이름
        </label>
        <input
          id="name"
          type="text"
          value={name || item?.name}
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
          value={price || item?.price}
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
          value={stockQuantity || item?.stockQuantity}
          onChange={handleStockQuantityChange}
          placeholder="상품 수량"
          className="w-[36.25rem] h-[3.75rem] indent-5 rounded-[35px] bg-gray-200"
        />
      </form>
      <div className="flex justify-between items-center mt-[2rem] mb-[2rem]">
        <h1 className="text-[1.75rem]">상품 이미지 변경</h1>
      </div>
      <form className="w-[36.25rem] flex justify-between text-xl" onSubmit={handleEditItemImage}>
        <div className="flex flex-col">
          <label htmlFor="content" className="indent-3 mb-2">
            상품 대표 이미지
          </label>
          <input
            id="content"
            type="file"
            onChange={handleItemImageChange}
            className="w-[30rem] h-[3.75rem] indent-5 rounded-[35px]"
          />
        </div>
        <button>변경하기</button>
      </form>
      <form className="w-[36.25rem] flex justify-between text-xl" onSubmit={handleEditItemInfoImage}>
        <div className="flex flex-col">
          <label htmlFor="content" className="indent-3 mb-2">
            상품 설명 이미지
          </label>
          <input
            id="content"
            type="file"
            onChange={handleItemInfoImageChange}
            className="w-[30rem] h-[3.75rem] indent-5 rounded-[35px]"
          />
        </div>
        <button>변경하기</button>
      </form>
    </div>
  );
};

export default EditItem;

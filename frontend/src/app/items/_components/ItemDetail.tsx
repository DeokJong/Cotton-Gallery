"use client";

import { Item } from "@/app/(main)/_components/GoodsCard";
import usePageStore from "@/store/pageStore";
import Image from "next/image";
import React, { useEffect, useState } from "react";
import { FaHeart, FaRegHeart } from "react-icons/fa";
import { FaChevronLeft } from "react-icons/fa6";
import { FaChevronRight } from "react-icons/fa6";
import { RxCross2 } from "react-icons/rx";
import { FaMinus } from "react-icons/fa6";
import { FaPlus } from "react-icons/fa6";
import CommonModal from "@/components/CommonModal";
import DeleteItemModal from "./DeleteItemModal";

type ItemDetailPropsType = {
  itemId: number;
};

const getItem = async (itemId: number) => {
  try {
    const response = await fetch(`http://localhost:8080//api/public/items/${itemId}`, {
      method: "GET",
      credentials: "include",
      headers: {
        Accept: "application/json"
      }
    });

    if (!response.ok) {
      throw new Error("아이템을 가져오는 데 실패했습니다.");
    }

    const data = await response.json();
    console.log(data);
    return data;
  } catch (error) {
    console.error("Error fetching item list:", error);
    return null;
  }
};

const ItemDetail = ({ itemId }: ItemDetailPropsType) => {
  // TODO: 상품 하나만 조회할 수 있는 api 요청 ---
  const { pageNumber } = usePageStore();
  const [isModalOpen, setIsModalOpen] = useState<boolean>(false);
  const [item, setItem] = useState<Item>();
  const [count, setCount] = useState<number>(0);
  const deliveryFee = 3000;

  const fetchItems = async (itemId: number) => {
    const result = await getItem(itemId);
    if (result) {
      console.log("아이템", result.data);
      //const data = result.data.items.find((item: Item) => item.itemId === +itemId);
      console.log(result.data);
      setItem(result.data);
    }
  };

  const handleDeleteBtn = () => {
    setIsModalOpen(true);
  };

  const handlemodifyBtn = async () => {};

  useEffect(() => {
    fetchItems(itemId);
    //eslint-disable-next-line
  }, [pageNumber]);

  return (
    <div className="flex gap-5">
      <div className="flex items-center gap-5">
        <button>
          <FaChevronLeft size={24} />
        </button>
        <Image src={"/pokeball.png"} width={500} height={500} priority alt="상품 사진" />
        <button>
          <FaChevronRight size={24} />
        </button>
      </div>
      <div className="w-[36.25rem]">
        <div className="flex justify-between">
          <p className="text-xl mb-4">판매자</p>
          {/* 판매자 본인이 판매하는 상품인 경우 수정·삭제 버튼 렌더링 */}
          <div className="flex text-bold text-white gap-3">
            <DeleteItemModal isModalOpen={isModalOpen} setIsModalOpen={setIsModalOpen} itemId={item?.itemId} />
            <button onClick={handleDeleteBtn} className="w-16 h-9 rounded-md bg-gray-400">
              삭제
            </button>
            <button onClick={handlemodifyBtn} className="w-16 h-9 rounded-md bg-gray-400">
              수정
            </button>
          </div>
        </div>
        <p className="text-[1.625rem] mb-4">{item?.name}</p>
        <div className="flex items-center gap-2 mb-10">
          <FaHeart size={24} />
          <p className="text-xl">{item?.likeCount.toLocaleString()}</p>
        </div>
        <p className="text-[2.188rem] font-bold mb-10">{item?.price.toLocaleString()}원</p>
        <div className="flex justify-between w-[12.5rem] mb-4">
          <p>배송비</p>
          <p>{deliveryFee.toLocaleString()}원</p>
        </div>
        <div className="flex flex-col p-3 justify-center gap-5 w-[33.438rem] h-[6.25rem] bg-gray-200">
          <div className="flex justify-between">
            <p className="w-[30rem] mt-1 ml-1 truncate">{item?.name}</p>
            <button>
              <RxCross2 className="cursor-pointer" size={24} />
            </button>
          </div>
          <div className="flex justify-between">
            <div className="flex justify-between items-center border-2 border-gray-400 w-[6.25rem]">
              <button onClick={() => setCount((prev) => (prev > 0 ? prev - 1 : prev))} className="ml-1">
                <FaMinus />
              </button>
              <p className=" text-center w-[2.813rem] font-[0.938rem] border-l-2 border-r-2 border-gray-400">{count}</p>
              <button onClick={() => setCount((prev) => prev + 1)} className="mr-1">
                <FaPlus />
              </button>
            </div>
            <p className="text-xl font-semibold">{((item?.price as number) * count).toLocaleString()}원</p>
          </div>
        </div>
        <div className="w-[33.438rem] flex justify-end">
          <div className="flex justify-between items-center w-[10.625rem] h-[5.313rem]">
            <p className="text-lg">합계</p>
            {/* 배송비 더한 값으로 변경 */}
            <p className="text-3xl font-bold text-sky-400">
              {count !== 0 ? ((item?.price as number) * count + deliveryFee).toLocaleString() : 0}원
            </p>
          </div>
        </div>
        <div className="flex justify-between w-[33.438rem] h-[3.125rem] text-[1.563rem] font-bold text-white">
          <div className="flex justify-center items-center w-[4.688rem] border-[0.188rem] border-gray-400 border-r-0">
            <button>
              {item?.likedByMe ? <FaHeart color="red" size={30} /> : <FaRegHeart size={30} color="gray" />}
            </button>
          </div>
          <button className="w-[14.375rem] bg-black">장바구니</button>
          <button className="w-[14.375rem] bg-sky-400">바로구매</button>
        </div>
      </div>
    </div>
  );
};

export default ItemDetail;

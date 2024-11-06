import Image from "next/image";
import React from "react";

const GoodsCard = () => {
  return (
    <div className="w-[23.75rem] h-[13.125rem] border-2 rounded-sm">
      <div className="flex flex-col">
        <div className="flex">
          <Image src={"/pokeball.png"} width={165} height={165} priority alt="상품 사진" />
          <div className="flex flex-col justify-between border-l-2">
            <div className="ml-1 mt-1">
              <p className="text-base">판매자</p>
              <p className="text-lg">브랜드명 상품명 용량및개수</p>
              <p className="font-bold text-xl">1,234원</p>
            </div>
            <div className="w-full h-[35px] pr-2 flex justify-end items-center border-t-2">
              {/* TODO: 아이콘으로 변경 */}
              <p>찜</p>
              <p>카트</p>
            </div>
          </div>
        </div>
        <div className="w-full h-[45px] pl-2 pr-2 flex justify-between items-center border-t-2">
          <p>★ 4.3 | 리뷰 (123)</p>
          <p>#해시태그</p>
        </div>
      </div>
    </div>
  );
};

export default GoodsCard;

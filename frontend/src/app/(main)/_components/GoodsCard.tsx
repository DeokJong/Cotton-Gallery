import Image from "next/image";
import React from "react";

interface DiscountResponse {
  discountId: number; // 할인 고유 ID
  name: string; // 할인 이름
  discountPercent: number; // 할인 퍼센트
  startDate: string; // 할인 시작 날짜 (ISO 형식)
  endDate: string; // 할인 종료 날짜 (ISO 형식)
}

export type Item = {
  itemId: number; // 아이템 고유 ID
  name: string; // 아이템 이름
  price: number; // 가격
  stockQuantity: number; // 재고 수량
  likeCount: number; // 좋아요 개수
  likedByMe: boolean; // 내가 좋아요를 눌렀는지 여부
  discountResponse: DiscountResponse | null; // 할인 정보 (null일 수도 있음)
};

type PropsType = {
  item: Item;
};

const GoodsCard = ({ item }: PropsType) => {
  return (
    <div className="w-[23.75rem] h-[13.125rem] border-2 rounded-sm">
      <div className="flex flex-col">
        <div className="flex">
          <Image src={"/pokeball.png"} width={165} height={165} priority alt="상품 사진" />
          <div className="w-[13.188rem] flex flex-col justify-between border-l-2">
            <div className="ml-1 mt-1">
              <p className="text-base">판매자</p>
              <p className="text-lg">{item.name}</p>
              <p className="font-bold text-xl">{item.price.toLocaleString()}원</p>
            </div>
            <div className="w-full h-[35px] pr-2 flex justify-end items-center border-t-2">
              {/* TODO: 아이콘으로 변경 */}
              {/* likedByMe에 따라 하트 아이콘 변경 ---- ? 상품 수정 api 질문 */}
              <p>찜 </p>
              <p>카트</p>
            </div>
          </div>
        </div>
        <div className="w-full h-[45px] pl-2 pr-2 flex justify-between items-center border-t-2">
          <p>♥ {item.likeCount} | 리뷰 (123)</p>
          <p>#해시태그</p>
        </div>
      </div>
    </div>
  );
};

export default GoodsCard;

import { create } from "zustand";
import { createJSONStorage, persist } from "zustand/middleware";

// Zustand store 정의
interface PageStore {
  pageNumber: number;
  setPageNumber: (page: number) => void;
}

const usePageStore = create(
  persist<PageStore>(
    (set) => ({
      pageNumber: 1, // 초기 페이지 숫자
      setPageNumber: (page: number) => set({ pageNumber: page })
    }),
    {
      name: "page-number-storage", // 로컬 스토리지의 key 이름
      storage: createJSONStorage(() => sessionStorage) // 로컬 스토리지를 사용
    }
  )
);

export default usePageStore;

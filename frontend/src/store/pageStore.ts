import { create } from "zustand";
import { createJSONStorage, persist } from "zustand/middleware";

interface PageStore {
  pageNumber: number;
  setPageNumber: (page: number) => void;
}

const usePageStore = create(
  persist<PageStore>(
    (set) => ({
      pageNumber: 1,
      setPageNumber: (page: number) => set({ pageNumber: page })
    }),
    {
      name: "page-number-storage",
      storage: createJSONStorage(() => sessionStorage)
    }
  )
);

export default usePageStore;

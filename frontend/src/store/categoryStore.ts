import { create } from "zustand";
import { createJSONStorage, persist } from "zustand/middleware";

interface CategoryStore {
  category: string;
  setCategory: (category: string) => void;
}

const useCategoryStore = create(
  persist<CategoryStore>(
    (set) => ({
      category: "CREATED_DATE",
      setCategory: (category: string) => set({ category })
    }),
    {
      name: "category-storage",
      storage: createJSONStorage(() => sessionStorage)
    }
  )
);

export default useCategoryStore;

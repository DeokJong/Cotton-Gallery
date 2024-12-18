import { create } from "zustand";
import { createJSONStorage, persist } from "zustand/middleware";

interface IsLoggedinStore {
  isLoggedin: boolean;
  setIsLoggedin: (isLoggedin: boolean) => void;
}

const useIsLoggedinStore = create(
  persist<IsLoggedinStore>(
    (set) => ({
      isLoggedin: false,
      setIsLoggedin: (isLoggedin: boolean) => set({ isLoggedin })
    }),
    {
      name: "isLoggedin-storage",
      storage: createJSONStorage(() => sessionStorage)
    }
  )
);

export default useIsLoggedinStore;

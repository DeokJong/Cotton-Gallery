import { ItemStoreType } from "@/types/item.type";
import { create } from "zustand";

export const useItemStore = create<ItemStoreType>((set) => ({
  name: "",
  price: 0,
  stockQuantity: 0,
  itemImage: null,
  itemInfoImage: null,

  setName: (name: string) => set({ name }),
  setPrice: (price: number) => set({ price }),
  setStockQuantity: (stockQuantity: number) => set({ stockQuantity }),
  setItemImage: (itemImage: File | null) => set({ itemImage }),
  setItemInfoImage: (itemInfoImage: File | null) => set({ itemInfoImage })
}));

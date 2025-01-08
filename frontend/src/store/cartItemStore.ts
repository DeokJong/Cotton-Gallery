import { CartItemStoreType } from "@/types/cartItem.type";
import { create } from "zustand";

export const useCartItemStore = create<CartItemStoreType>((set) => ({
  cartItemId: 0,
  discountPercent: 0,
  name: "",
  itemStatus: "",
  price: 0,
  quantity: 0,

  setCartItemId: (cartItemId: number) => set({ cartItemId }),
  setDiscountPercent: (discountPercent: number) => set({ discountPercent }),
  setItemStatus: (itemStatus: string) => set({ itemStatus }),
  setName: (name: string) => set({ name }),
  setPrice: (price: number) => set({ price }),
  setQuantity: (quantity: number) => set({ quantity })
}));

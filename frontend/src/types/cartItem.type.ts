export type CartItemStoreType = {
  cartItemId: number;
  discountPercent: number;
  name: string;
  itemStatus: string;
  price: number;
  quantity: number;

  setCartItemId: (cartItemId: number) => void;
  setDiscountPercent: (discountPercent: number) => void;
  setItemStatus: (itemStatus: string) => void;
  setName: (name: string) => void;
  setPrice: (price: number) => void;
  setQuantity: (quantity: number) => void;
};

export type ItemStoreType = {
  name: string;
  price: number;
  stockQuantity: number;
  itemImage: File | null;
  itemInfoImage: File | null;

  setName: (name: string) => void;
  setPrice: (price: number) => void;
  setStockQuantity: (stockQuantity: number) => void;
  setItemImage: (itemImage: File | null) => void;
  setItemInfoImage: (itemInfoImage: File | null) => void;
};

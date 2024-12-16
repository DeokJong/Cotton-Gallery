export type ItemStoreType = {
  name: string;
  price: number;
  stockQuantity: number;
  content: string;

  setName: (name: string) => void;
  setPrice: (price: number) => void;
  setStockQuantity: (stockQuantity: number) => void;
  setContent: (content: string) => void;
};

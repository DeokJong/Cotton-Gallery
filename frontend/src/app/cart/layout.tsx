import { PropsWithChildren } from "react";
import CartHeader from "./_components/CartHeader";

const CartLayout = ({ children }: PropsWithChildren) => {
  return (
    <div className="w-full h-screen flex- flex-col overflow-y-auto scrollbar-hide scroll-smooth">
      <CartHeader />
      <div className="flex flex-col flex-grow relative">{children}</div>
    </div>
  );
};

export default CartLayout;

import { PropsWithChildren } from "react";
import OrdersheetHeader from "./_components/OrdersheetHeader";

const OrdersheetLayout = ({ children }: PropsWithChildren) => {
  return (
    <div className="w-full h-screen  flex-col overflow-y-auto scrollbar-hide scroll-smooth">
      <OrdersheetHeader />
      <div className="flex flex-col flex-grow relative">{children}</div>
    </div>
  );
};

export default OrdersheetLayout;

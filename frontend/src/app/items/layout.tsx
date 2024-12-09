import { PropsWithChildren } from "react";
import ItemsHeader from "./_components/ItemsHeader";

const ItemsLayout = ({ children }: PropsWithChildren) => {
  return (
    <div className="w-full h-screen flex- flex-col overflow-y-auto scrollbar-hide scroll-smooth">
      <ItemsHeader />
      <div className="flex flex-col flex-grow relative">{children}</div>
    </div>
  );
};

export default ItemsLayout;

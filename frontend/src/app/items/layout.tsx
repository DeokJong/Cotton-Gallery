import { PropsWithChildren } from "react";
import MainHeader from "../(main)/_components/MainHeader";

const ItemsLayout = ({ children }: PropsWithChildren) => {
  return (
    <div className="w-full h-screen flex- flex-col overflow-y-auto scrollbar-hide scroll-smooth">
      <MainHeader />
      <div className="flex flex-col flex-grow relative items-center">{children}</div>
    </div>
  );
};

export default ItemsLayout;

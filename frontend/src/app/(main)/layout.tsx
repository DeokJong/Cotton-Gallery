import { PropsWithChildren } from "react";
import MainHeader from "./_components/MainHeader";

const MainLayout = ({ children }: PropsWithChildren) => {
  return (
    <div className="w-full h-screen flex- flex-col overflow-y-auto scrollbar-hide scroll-smooth">
      <MainHeader />
      <div className="flex flex-col flex-grow relative">{children}</div>
    </div>
  );
};

export default MainLayout;

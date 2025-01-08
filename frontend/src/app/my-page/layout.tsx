import { PropsWithChildren } from "react";
import MyPageHeader from "./_components/MyPageHeader";

const MyPageLayout = ({ children }: PropsWithChildren) => {
  return (
    <div className="w-full h-screen flex- flex-col overflow-y-auto scrollbar-hide scroll-smooth">
      <MyPageHeader />
      <div className="flex flex-col flex-grow relative">{children}</div>
    </div>
  );
};

export default MyPageLayout;

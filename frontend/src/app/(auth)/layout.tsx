import { PropsWithChildren } from "react";

const AuthLayout = ({ children }: PropsWithChildren) => {
  return (
    <div className="w-full h-screen flex- flex-col overflow-y-auto scrollbar-hide scroll-smooth">
      {/* <AuthHeader/> */}
      <div className="flex flex-col flex-grow relative">{children}</div>
    </div>
  );
};

export default AuthLayout;

import Logo from "@/components/Logo";
import Link from "next/link";

const AuthHeader = () => {
  return (
    <div className="w-full mt-[3.75rem] mb-[3.75rem] flex justify-center">
      <Link href={"/"}>
        <Logo />
      </Link>
    </div>
  );
};

export default AuthHeader;

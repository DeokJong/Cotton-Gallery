type PropsType = {
  text: string;
  isLoginBtn: boolean;
};

const SubmitBtn = ({ text }: PropsType) => {
  return (
    <div>
      <button>{text}</button>
    </div>
  );
};

export default SubmitBtn;

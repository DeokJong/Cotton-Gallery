export type AuthStoreType = {
  // auth 타입 작성
  username: string;
  password: string;
  passwordConfirm: string;
  name: string;
  phoneNumber: string;
  email: string;
  error: {
    username: string;
    password: string;
    passwordConfirm: string;
    name: string;
    phoneNumber: string;
    email: string;
  };

  setUsername: (username: string) => void;
  setPassword: (password: string) => void;
  setPasswordConfirm: (passwordConfirm: string) => void;
  setName: (name: string) => void;
  setPhoneNumber: (phoneNumber: string) => void;
  setEmail: (email: string) => void;
  setError: (error: Partial<AuthStoreType["error"]>) => void;
};

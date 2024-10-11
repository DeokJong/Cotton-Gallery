export type AuthStoreType = {
  username: string;
  password: string;
  passwordConfirm: string;
  name: string;
  phoneNumber: string;
  email: string;
  zipcode: number;
  street: string;
  detail: string;
  error: {
    username: string;
    password: string;
    passwordConfirm: string;
    name: string;
    phoneNumber: string;
    email: string;
    zipcode: string;
    street: string;
    detail: string;
  };

  setUsername: (username: string) => void;
  setPassword: (password: string) => void;
  setPasswordConfirm: (passwordConfirm: string) => void;
  setName: (name: string) => void;
  setPhoneNumber: (phoneNumber: string) => void;
  setEmail: (email: string) => void;
  setZipcode: (zipcode: number) => void;
  setStreet: (street: string) => void;
  setDetail: (detail: string) => void;
  setError: (error: Partial<AuthStoreType["error"]>) => void;
};

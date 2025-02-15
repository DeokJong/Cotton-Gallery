export type AuthStoreType = {
  username: string;
  password: string;
  passwordConfirm: string;
  name: string;
  phoneNumber: string;
  email: string;
  zipcode: string;
  street: string;
  detail: string;
  isLoggedin?: boolean;
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
  setZipcode: (zipcode: string) => void;
  setStreet: (street: string) => void;
  setDetail: (detail: string) => void;
  setIsLoggedin: (isLoggedin: boolean) => void;
  setError: (field: string, message: string) => void;
};

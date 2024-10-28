import { AuthStoreType } from "@/types/auth.type";
import { create } from "zustand";

export const useAuthStore = create<AuthStoreType>((set) => ({
  username: "",
  password: "",
  passwordConfirm: "",
  name: "",
  phoneNumber: "",
  email: "",
  zipcode: "",
  street: "",
  detail: "",
  error: {
    username: "",
    password: "",
    passwordConfirm: "",
    name: "",
    phoneNumber: "",
    email: "",
    zipcode: "",
    street: "",
    detail: ""
  },

  setUsername: (username: string) => set({ username }),
  setPassword: (password: string) => set({ password }),
  setPasswordConfirm: (passwordConfirm: string) => set({ passwordConfirm }),
  setName: (name: string) => set({ name }),
  setPhoneNumber: (phoneNumber: string) => set({ phoneNumber }),
  setEmail: (email: string) => set({ email }),
  setZipcode: (zipcode: string) => set({ zipcode }),
  setStreet: (street: string) => set({ street }),
  setDetail: (detail: string) => set({ detail }),
  setError: (field: string, message: string) =>
    set((state) => ({
      error: {
        ...state.error,
        [field]: message
      }
    }))
}));

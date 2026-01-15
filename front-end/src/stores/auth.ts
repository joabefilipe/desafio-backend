import { defineStore } from "pinia";
import http from "../api/http";

type LoginPayload = {
  login: string;
  password: string;
};

export const useAuthStore = defineStore("auth", {
  state: () => ({
    token: localStorage.getItem("token") as string | null,
  }),
  getters: {
    isAuthenticated: (state) => !!state.token,
  },
  actions: {
    async login(payload: LoginPayload) {
      const { data } = await http.post("/auth/login", payload);

      const token = data.token ?? data.accessToken ?? data;

      this.token = token;
      localStorage.setItem("token", token);
    },
    logout() {
      this.token = null;
      localStorage.removeItem("token");
    },
  },
});

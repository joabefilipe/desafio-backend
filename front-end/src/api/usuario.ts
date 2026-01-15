import http from "./http";

export type UserRole = "ADMIN" | "USER";

export type RegistrarPayload = {
    login: string;
    password: string;
    role: UserRole;
};

export async function registrarUsuario(payload: RegistrarPayload) {
  return http.post("/usuario/cadastro", payload);
}

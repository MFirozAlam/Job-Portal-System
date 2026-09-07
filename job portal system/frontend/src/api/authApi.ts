import { api } from "./axios";
import type { User } from "./types";

export interface LoginRequest { email: string; password: string; }
export interface RegisterRequest { name: string; email: string; password: string; role: "CANDIDATE" | "EMPLOYER"; }

export const login = async (data: LoginRequest) => (await api.post<User>("/auth/login", data)).data;
export const register = async (data: RegisterRequest) => (await api.post<User>("/auth/register", data)).data;
export const logout = async () => { await api.post("/auth/logout"); };
export const getCurrentUser = async () => (await api.get<User>("/auth/me")).data;

import { api } from "./axios";
import type { Application, Job, User } from "./types";

export const getUsers = async () => (await api.get<User[]>("/admin/users")).data;
export const deleteUser = async (id: number) => { await api.delete(`/admin/users/${id}`); };
export const getAdminJobs = async () => (await api.get<Job[]>("/admin/jobs")).data;
export const deleteAdminJob = async (id: number) => { await api.delete(`/admin/jobs/${id}`); };
export const getAllApplications = async () => (await api.get<Application[]>("/admin/applications")).data;

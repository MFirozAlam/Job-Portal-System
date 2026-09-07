import { api } from "./axios";
import type { Job, JobRequest } from "./types";

export const getJobs = async () => (await api.get<Job[]>("/jobs")).data;
export const searchJobs = async (params: Record<string, string | number>) =>
  (await api.get<Job[]>("/jobs/search", { params })).data;
export const getJob = async (id: number) => (await api.get<Job>(`/jobs/${id}`)).data;
export const getMyJobs = async () => (await api.get<Job[]>("/jobs/mine")).data;
export const createJob = async (data: JobRequest) => (await api.post<Job>("/jobs", data)).data;
export const updateJob = async (id: number, data: JobRequest) => (await api.put<Job>(`/jobs/${id}`, data)).data;
export const deleteJob = async (id: number) => { await api.delete(`/jobs/${id}`); };

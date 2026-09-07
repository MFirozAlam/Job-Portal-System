import { api } from "./axios";
import type { Application, ApplicationStatus } from "./types";

export const applyToJob = async (jobId: number) => (await api.post<Application>(`/applications/jobs/${jobId}`)).data;
export const getMyApplications = async () => (await api.get<Application[]>("/applications/mine")).data;
export const getJobApplications = async (jobId: number) => (await api.get<Application[]>(`/applications/jobs/${jobId}`)).data;
export const updateApplicationStatus = async (id: number, status: ApplicationStatus) =>
  (await api.put<Application>(`/applications/${id}/status`, { status })).data;

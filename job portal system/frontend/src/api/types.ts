export type Role = "CANDIDATE" | "EMPLOYER" | "ADMIN";
export type ApplicationStatus = "APPLIED" | "SHORTLISTED" | "REJECTED";

export interface User {
  id: number;
  name: string;
  email: string;
  role: Role;
}
export interface Job {
  id: number;
  title: string;
  companyName: string;
  location: string;
  salary: number;
  jobType: string;
  description: string;
  requiredSkills: string;
  employerId?: number;
  employerName?: string;
}
export interface Application {
  id: number;
  jobId: number;
  jobTitle: string;
  companyName: string;
  candidateId: number;
  candidateName: string;
  status: ApplicationStatus;
  appliedAt: string;
}
export interface JobRequest {
  title: string;
  companyName: string;
  location: string;
  salary: number;
  jobType: string;
  description: string;
  requiredSkills: string;
}

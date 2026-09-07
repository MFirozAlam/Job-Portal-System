package com.jobportal.service;

import com.jobportal.dto.JobDto;
import com.jobportal.dto.JobRequest;
import com.jobportal.entity.Job;
import java.util.List;

public interface JobService {

    JobDto create(Long userId, JobRequest request);

    JobDto update(Long userId, Long id, JobRequest request);

    void delete(Long userId, Long id);

    List<JobDto> getAll();

    JobDto getById(Long id);

    List<JobDto> getEmployerJobs(Long userId);

    List<JobDto> search(String keyword, String location, String jobType, Double minSalary, Double maxSalary);

    Job getRequired(Long id);
}

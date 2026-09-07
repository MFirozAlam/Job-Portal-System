package com.jobportal.service;

import com.jobportal.dto.ApplicationDto;
import com.jobportal.dto.StatusUpdateRequest;
import java.util.List;

public interface ApplicationService {

    ApplicationDto apply(Long candidateId, Long jobId);

    List<ApplicationDto> getCandidateApplications(Long candidateId);

    List<ApplicationDto> getJobApplications(Long employerId, Long jobId);

    ApplicationDto updateStatus(Long employerId, Long applicationId, StatusUpdateRequest request);

    List<ApplicationDto> getAll();
}

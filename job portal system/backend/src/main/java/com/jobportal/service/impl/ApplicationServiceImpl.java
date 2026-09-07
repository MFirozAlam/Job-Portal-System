package com.jobportal.service.impl;

import com.jobportal.dto.*;
import com.jobportal.entity.*;
import com.jobportal.repository.ApplicationRepository;
import com.jobportal.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.*;

@Service @RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {
    private final ApplicationRepository apps;
    private final JobService jobs;
    private final UserService users;

    public ApplicationDto apply(Long cid,Long jid) {
        User c=users.getRequiredUser(cid); if(c.getRole()!=Role.CANDIDATE) throw new IllegalStateException("Only candidates can apply");
        Job j=jobs.getRequired(jid);
        if(apps.existsByJobAndCandidate(j,c)) throw new IllegalStateException("You already applied for this job");
        JobApplication a=JobApplication.builder().job(j).candidate(c).status(ApplicationStatus.APPLIED).appliedAt(LocalDateTime.now()).build();
        return toDto(apps.save(a));
    }
    public List<ApplicationDto> getCandidateApplications(Long cid) {
        User c=users.getRequiredUser(cid); if(c.getRole()!=Role.CANDIDATE) throw new IllegalStateException("Only candidates can view applications");
        return apps.findByCandidateOrderByAppliedAtDesc(c).stream().map(this::toDto).toList();
    }
    public List<ApplicationDto> getJobApplications(Long eid,Long jid) {
        Job j=jobs.getRequired(jid); owner(eid,j);
        return apps.findByJobOrderByAppliedAtDesc(j).stream().map(this::toDto).toList();
    }
    public ApplicationDto updateStatus(Long eid,Long aid,StatusUpdateRequest r) {
        JobApplication a=apps.findById(aid).orElseThrow(()->new IllegalArgumentException("Application not found: "+aid));
        owner(eid,a.getJob()); a.setStatus(r.getStatus()); return toDto(apps.save(a));
    }
    public List<ApplicationDto> getAll() { return apps.findAllByOrderByAppliedAtDesc().stream().map(this::toDto).toList(); }
    private void owner(Long eid,Job j) { if(!Objects.equals(j.getEmployer().getId(),eid)) throw new IllegalStateException("Access denied"); }
    private ApplicationDto toDto(JobApplication a) { return ApplicationDto.builder().id(a.getId()).jobId(a.getJob().getId()).jobTitle(a.getJob().getTitle()).companyName(a.getJob().getCompanyName()).candidateId(a.getCandidate().getId()).candidateName(a.getCandidate().getName()).status(a.getStatus()).appliedAt(a.getAppliedAt()).build(); }
}

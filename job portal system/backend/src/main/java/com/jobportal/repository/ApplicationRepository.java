package com.jobportal.repository;

import com.jobportal.entity.JobApplication;
import com.jobportal.entity.Job;
import com.jobportal.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ApplicationRepository extends JpaRepository<JobApplication,Long> {
    boolean existsByJobAndCandidate(Job job, User candidate);

    List<JobApplication> findByCandidateOrderByAppliedAtDesc(User candidate);

    List<JobApplication> findByJobOrderByAppliedAtDesc(Job job);

    List<JobApplication> findAllByOrderByAppliedAtDesc();
}

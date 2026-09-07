package com.jobportal.service.impl;

import com.jobportal.dto.JobDto;
import com.jobportal.dto.JobRequest;
import com.jobportal.entity.Job;
import com.jobportal.entity.Role;
import com.jobportal.entity.User;
import com.jobportal.repository.JobRepository;
import com.jobportal.service.JobService;
import com.jobportal.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {

    private final JobRepository jobs;
    private final UserService users;

    // =========================
    // CREATE JOB
    // =========================
    @Override
    public JobDto create(Long uid, JobRequest request) {

        User user = users.getRequiredUser(uid);

        require(user, Role.EMPLOYER);

        Job job = copy(new Job(), request);

        job.setEmployer(user);

        Job savedJob = jobs.save(job);

        return toDto(savedJob);
    }

    // =========================
    // UPDATE JOB
    // =========================
    @Override
    public JobDto update(Long uid, Long id, JobRequest request) {

        Job job = getRequired(id);

        checkOwner(uid, job);

        copy(job, request);

        Job updatedJob = jobs.save(job);

        return toDto(updatedJob);
    }

    // =========================
    // DELETE JOB
    // =========================
    @Override
    public void delete(Long uid, Long id) {

        Job job = getRequired(id);

        checkOwner(uid, job);

        jobs.delete(job);
    }

    // =========================
    // GET ALL JOBS
    // =========================
    @Override
    public List<JobDto> getAll() {

        return jobs.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    // =========================
    // GET JOB BY ID
    // =========================
    @Override
    public JobDto getById(Long id) {

        Job job = getRequired(id);

        return toDto(job);
    }

    // =========================
    // GET EMPLOYER JOBS
    // =========================
    @Override
    public List<JobDto> getEmployerJobs(Long uid) {

        User user = users.getRequiredUser(uid);

        require(user, Role.EMPLOYER);

        return jobs.findByEmployerOrderByIdDesc(user)
                .stream()
                .map(this::toDto)
                .toList();
    }

    // =========================
    // SEARCH / FILTER JOBS
    // =========================
    @Override
    public List<JobDto> search(
            String keyword,
            String location,
            String jobType,
            Double minSalary,
            Double maxSalary) {

        Specification<Job> specification = Specification.allOf();

        // Keyword search
        if (keyword != null && !keyword.isBlank()) {

            String key = keyword.trim().toLowerCase();

            specification = specification.and(
                    (root, query, criteriaBuilder) ->
                            criteriaBuilder.or(

                                    criteriaBuilder.like(
                                            criteriaBuilder.lower(
                                                    root.get("title")
                                            ),
                                            "%" + key + "%"
                                    ),

                                    criteriaBuilder.like(
                                            criteriaBuilder.lower(
                                                    root.get("companyName")
                                            ),
                                            "%" + key + "%"
                                    ),

                                    criteriaBuilder.like(
                                            criteriaBuilder.lower(
                                                    root.get("requiredSkills")
                                            ),
                                            "%" + key + "%"
                                    ),

                                    criteriaBuilder.like(
                                            criteriaBuilder.lower(
                                                    root.get("description")
                                            ),
                                            "%" + key + "%"
                                    )
                            )
            );
        }

        // Location filter
        if (location != null && !location.isBlank()) {

            String key = location.trim().toLowerCase();

            specification = specification.and(
                    (root, query, criteriaBuilder) ->
                            criteriaBuilder.like(
                                    criteriaBuilder.lower(
                                            root.get("location")
                                    ),
                                    "%" + key + "%"
                            )
            );
        }

        // Job type filter
        if (jobType != null && !jobType.isBlank()) {

            String type = jobType.trim().toLowerCase();

            specification = specification.and(
                    (root, query, criteriaBuilder) ->
                            criteriaBuilder.equal(
                                    criteriaBuilder.lower(
                                            root.get("jobType")
                                    ),
                                    type
                            )
            );
        }

        // Minimum salary
        if (minSalary != null) {

            specification = specification.and(
                    (root, query, criteriaBuilder) ->
                            criteriaBuilder.greaterThanOrEqualTo(
                                    root.get("salary"),
                                    minSalary
                            )
            );
        }

        // Maximum salary
        if (maxSalary != null) {

            specification = specification.and(
                    (root, query, criteriaBuilder) ->
                            criteriaBuilder.lessThanOrEqualTo(
                                    root.get("salary"),
                                    maxSalary
                            )
            );
        }

        return jobs.findAll(specification)
                .stream()
                .map(this::toDto)
                .toList();
    }

    // =========================
    // GET REQUIRED JOB
    // =========================
    @Override
    public Job getRequired(Long id) {

        return jobs.findById(id)
                .orElseThrow(
                        () -> new IllegalArgumentException(
                                "Job not found: " + id
                        )
                );
    }

    // =========================
    // CHECK JOB OWNER
    // =========================
    private void checkOwner(Long uid, Job job) {

        if (job.getEmployer() == null ||
                !Objects.equals(
                        job.getEmployer().getId(),
                        uid
                )) {

            throw new IllegalStateException(
                    "You can manage only your own jobs"
            );
        }
    }

    // =========================
    // CHECK USER ROLE
    // =========================
    private void require(User user, Role role) {

        if (user == null ||
                user.getRole() != role) {

            throw new IllegalStateException(
                    "Access denied: " + role +
                            " role required"
            );
        }
    }

    // =========================
    // COPY REQUEST -> ENTITY
    // =========================
    private Job copy(Job job, JobRequest request) {

        job.setTitle(request.getTitle());

        job.setCompanyName(
                request.getCompanyName()
        );

        job.setLocation(
                request.getLocation()
        );

        job.setSalary(
                request.getSalary()
        );

        job.setJobType(
                request.getJobType()
        );

        job.setDescription(
                request.getDescription()
        );

        job.setRequiredSkills(
                request.getRequiredSkills()
        );

        return job;
    }

    // =========================
    // ENTITY -> DTO
    // =========================
    private JobDto toDto(Job job) {

        return JobDto.builder()
                .id(job.getId())
                .title(job.getTitle())
                .companyName(job.getCompanyName())
                .location(job.getLocation())
                .salary(job.getSalary())
                .jobType(job.getJobType())
                .description(job.getDescription())
                .requiredSkills(job.getRequiredSkills())
                .employerId(
                        job.getEmployer() != null
                                ? job.getEmployer().getId()
                                : null
                )
                .employerName(
                        job.getEmployer() != null
                                ? job.getEmployer().getName()
                                : null
                )
                .build();
    }
}

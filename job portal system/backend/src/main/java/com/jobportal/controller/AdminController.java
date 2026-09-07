package com.jobportal.controller;

import com.jobportal.dto.ApplicationDto;
import com.jobportal.dto.JobDto;
import com.jobportal.dto.UserDto;
import com.jobportal.entity.Role;
import com.jobportal.entity.User;
import com.jobportal.repository.JobRepository;
import com.jobportal.service.ApplicationService;
import com.jobportal.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService users;
    private final JobRepository jobs;
    private final ApplicationService apps;

    private void check(HttpSession session) {
        Long currentUserId = AuthController.currentId(session);

        UserDto user = users.getUserById(currentUserId);

        if (user.getRole() != Role.ADMIN) {
            throw new IllegalStateException("Admin access required");
        }
    }

    @GetMapping("/users")
    public List<UserDto> userList(HttpSession session) {
        check(session);

        return users.getAllUsers();
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(
            @PathVariable Long id,
            HttpSession session
    ) {
        check(session);

        users.deleteUser(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/jobs")
    public List<JobDto> jobList(HttpSession session) {
        check(session);

        return jobs.findAll()
                .stream()
                .map(job -> JobDto.builder()
                        .id(job.getId())
                        .title(job.getTitle())
                        .companyName(job.getCompanyName())
                        .location(job.getLocation())
                        .salary(job.getSalary())
                        .jobType(job.getJobType())
                        .description(job.getDescription())
                        .requiredSkills(job.getRequiredSkills())
                        .employerId(job.getEmployer().getId())
                        .employerName(job.getEmployer().getName())
                        .build()
                )
                .toList();
    }

    @DeleteMapping("/jobs/{id}")
    public ResponseEntity<Void> deleteJob(
            @PathVariable Long id,
            HttpSession session
    ) {
        check(session);

        if (!jobs.existsById(id)) {
            throw new IllegalArgumentException("Job not found");
        }

        jobs.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/applications")
    public List<ApplicationDto> applications(HttpSession session) {
        check(session);

        return apps.getAll();
    }
}
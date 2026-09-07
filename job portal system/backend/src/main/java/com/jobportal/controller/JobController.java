package com.jobportal.controller;

import com.jobportal.dto.JobDto;
import com.jobportal.dto.JobRequest;
import com.jobportal.service.JobService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
@RequiredArgsConstructor
public class JobController {

    private final JobService jobService;

    @GetMapping
    public List<JobDto> getAllJobs() {
        return jobService.getAll();
    }

    @GetMapping("/search")
    public List<JobDto> searchJobs(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String jobType,
            @RequestParam(required = false) Double minSalary,
            @RequestParam(required = false) Double maxSalary) {
        return jobService.search(keyword, location, jobType, minSalary, maxSalary);
    }

    @GetMapping("/{id}")
    public JobDto getJobById(@PathVariable Long id) {
        return jobService.getById(id);
    }

    @GetMapping("/mine")
    public List<JobDto> getMyJobs(HttpSession session) {
        return jobService.getEmployerJobs(AuthController.currentId(session));
    }

    @PostMapping
    public ResponseEntity<JobDto> createJob(@Valid @RequestBody JobRequest request, HttpSession session) {
        JobDto createdJob = jobService.create(AuthController.currentId(session), request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdJob);
    }

    @PutMapping("/{id}")
    public JobDto updateJob(@PathVariable Long id, @Valid @RequestBody JobRequest request, HttpSession session) {
        return jobService.update(AuthController.currentId(session), id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJob(@PathVariable Long id, HttpSession session) {
        jobService.delete(AuthController.currentId(session), id);
        return ResponseEntity.noContent().build();
    }
}
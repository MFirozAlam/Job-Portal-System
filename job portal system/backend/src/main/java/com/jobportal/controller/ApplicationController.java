package com.jobportal.controller;

import com.jobportal.dto.ApplicationDto;
import com.jobportal.dto.StatusUpdateRequest;
import com.jobportal.service.ApplicationService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationService apps;

    @PostMapping("/jobs/{jobId}")
    public ApplicationDto apply(
            @PathVariable Long jobId,
            HttpSession session
    ) {
        return apps.apply(
                AuthController.currentId(session),
                jobId
        );
    }

    @GetMapping("/mine")
    public List<ApplicationDto> mine(HttpSession session) {
        return apps.getCandidateApplications(
                AuthController.currentId(session)
        );
    }

    @GetMapping("/jobs/{jobId}")
    public List<ApplicationDto> forJob(
            @PathVariable Long jobId,
            HttpSession session
    ) {
        return apps.getJobApplications(
                AuthController.currentId(session),
                jobId
        );
    }

    @PutMapping("/{applicationId}/status")
    public ApplicationDto status(
            @PathVariable Long applicationId,
            @Valid @RequestBody StatusUpdateRequest request,
            HttpSession session
    ) {
        return apps.updateStatus(
                AuthController.currentId(session),
                applicationId,
                request
        );
    }
}
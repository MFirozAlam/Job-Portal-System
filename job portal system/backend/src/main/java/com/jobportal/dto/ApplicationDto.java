package com.jobportal.dto;

import com.jobportal.entity.ApplicationStatus;
import lombok.*;
import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ApplicationDto {
    private Long id;
    private Long jobId;
    private String jobTitle;
    private String companyName;
    private Long candidateId;
    private String candidateName;
    private ApplicationStatus status;
    private LocalDateTime appliedAt;
}

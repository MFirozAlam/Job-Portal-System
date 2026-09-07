package com.jobportal.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class JobRequest {
    @NotBlank private String title;
    @NotBlank private String companyName;
    @NotBlank private String location;
    @NotNull @PositiveOrZero private Double salary;
    @NotBlank private String jobType;
    @NotBlank @Size(max=2000) private String description;
    @NotBlank @Size(max=1000) private String requiredSkills;
}

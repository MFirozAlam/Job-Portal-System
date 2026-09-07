package com.jobportal.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class JobDto {
    private Long id;
    private String title;
    private String companyName;
    private String location;
    private Double salary;
    private String jobType;
    private String description;
    private String requiredSkills;
    private Long employerId;
    private String employerName;
}

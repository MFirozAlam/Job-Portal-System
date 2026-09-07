package com.jobportal.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "jobs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String companyName;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private Double salary;

    @Column(nullable = false)
    private String jobType;

    @Column(nullable = false, length = 2000)
    private String description;

    @Column(nullable = false, length = 1000)
    private String requiredSkills;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "employer_id", nullable = false)
    private User employer;
}
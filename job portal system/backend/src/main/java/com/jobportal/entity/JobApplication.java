package com.jobportal.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name="applications", uniqueConstraints=@UniqueConstraint(columnNames={"job_id","candidate_id"}))
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class JobApplication {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch=FetchType.LAZY, optional=false) @JoinColumn(name="job_id", nullable=false)
    private Job job;
    @ManyToOne(fetch=FetchType.LAZY, optional=false) @JoinColumn(name="candidate_id", nullable=false)
    private User candidate;
    @Enumerated(EnumType.STRING) @Column(nullable=false)
    private ApplicationStatus status;
    @Column(nullable=false) private LocalDateTime appliedAt;
}

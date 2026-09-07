package com.jobportal.dto;

import com.jobportal.entity.ApplicationStatus;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class StatusUpdateRequest {
    @NotNull
    private ApplicationStatus status;
}

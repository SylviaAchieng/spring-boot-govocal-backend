package com.example.engagement_platform.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProjectCommentDto {
    private BigDecimal id;
    private Long userId;
    private BigDecimal projectId;
    private LocalDateTime createdAt;
}

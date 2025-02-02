package com.example.engagement_platform.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProjectsDto {
    private Long userId;
    private String title;
    private String description;
    private String base64EncodedImage;
    private BigDecimal daysRemaining;
    private String tag;
}

package com.example.engagement_platform.model.dto.response;

import com.example.engagement_platform.model.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProjectsDto {
    private BigDecimal projectId;
    private UserDto user;
    private String title;
    private String description;
    private String base64EncodedImage;
    private BigDecimal daysRemaining;
    private String tag;
    private LocationDto location;
    private BigDecimal approximateCost;
    private BigDecimal actualCost;
    private LocalDate startDate;
    private LocalDate endDate;
    private String base64Encoded;

}

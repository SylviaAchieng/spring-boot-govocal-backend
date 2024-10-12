package com.example.engagement_platform.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class PublicServantDto {
    private String department;
    private String position;
}

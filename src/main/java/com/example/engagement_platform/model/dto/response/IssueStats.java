package com.example.engagement_platform.model.dto.response;

import com.example.engagement_platform.enums.IssueStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IssueStats {
    private Map<IssueStatusEnum, Integer> statusCount;
}

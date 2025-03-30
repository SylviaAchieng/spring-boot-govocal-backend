package com.example.engagement_platform.model.dto.response;

import com.example.engagement_platform.enums.NotificationStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotificationStats {
    private Map<NotificationStatusEnum, Integer> statusCounts;
}

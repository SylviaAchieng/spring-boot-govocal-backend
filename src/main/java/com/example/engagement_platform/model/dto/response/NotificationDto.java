package com.example.engagement_platform.model.dto.response;

import com.example.engagement_platform.enums.NotificationStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDto {
    private Long notificationId;

    private String type;

    private String description;

    private Timestamp sentAt;

    private NotificationStatusEnum status;

    private Long userId;

    private Long locationId;

}

package com.example.engagement_platform.service;

import com.example.engagement_platform.common.GenericResponseV2;
import com.example.engagement_platform.model.Notification;
import com.example.engagement_platform.model.dto.response.NotificationDto;

import java.util.List;

public interface NotificationService {
    GenericResponseV2<List<NotificationDto>> getAllNotifications();

    GenericResponseV2<NotificationDto> createNotification(NotificationDto notificationDto);

    GenericResponseV2<NotificationDto> getNotificationById(Long notificationId);

    GenericResponseV2<Boolean> deleteNotificationById(Long notificationId);

    GenericResponseV2<Boolean> updateNotificationById(NotificationDto notificationDto, Long notificationId);
}

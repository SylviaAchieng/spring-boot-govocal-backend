package com.example.engagement_platform.service;

import com.example.engagement_platform.common.GenericResponseV2;
import com.example.engagement_platform.common.GenericResponseV3;
import com.example.engagement_platform.model.dto.response.NotificationDto;
import com.example.engagement_platform.model.dto.response.NotificationStats;

import java.util.List;

public interface NotificationService {
    GenericResponseV3<List<NotificationDto>, NotificationStats> getAllNotifications();

    GenericResponseV2<NotificationDto> createNotification(NotificationDto notificationDto);

    GenericResponseV2<NotificationDto> getNotificationById(Long notificationId);

    GenericResponseV2<Boolean> deleteNotificationById(Long notificationId);

    GenericResponseV2<Boolean> updateNotificationById(NotificationDto notificationDto, Long notificationId);

    GenericResponseV3<List<NotificationDto>, NotificationStats> getAllNotificationsByUserId(Long userId);

    GenericResponseV3<List<NotificationDto>, NotificationStats> getAllNotificationsByLocationId(Long locationId);

    GenericResponseV3<String, Void> markAllNotificationsAsRead();

}

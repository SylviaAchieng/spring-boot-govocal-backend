package com.example.engagement_platform.service;

import com.example.engagement_platform.model.Events;
import com.example.engagement_platform.model.Notifications;

import java.util.List;

public interface NotificationService {
    List<Notifications> getAllNotifications();

    Notifications createNotification(Notifications notifications);

    Notifications getNotificationById(Long notificationId);

    void deleteNotificationById(Long notificationId);

    void updateNotificationById(Notifications notifications, Long notificationId);
}

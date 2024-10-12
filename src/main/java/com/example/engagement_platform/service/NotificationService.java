package com.example.engagement_platform.service;

import com.example.engagement_platform.model.Notification;

import java.util.List;

public interface NotificationService {
    List<Notification> getAllNotifications();

    Notification createNotification(Notification notifications);

    Notification getNotificationById(Long notificationId);

    void deleteNotificationById(Long notificationId);

    void updateNotificationById(Notification notifications, Long notificationId);
}

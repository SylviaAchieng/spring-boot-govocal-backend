package com.example.engagement_platform.repository;

import com.example.engagement_platform.enums.NotificationStatusEnum;
import com.example.engagement_platform.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    Optional<Notification> findByNotificationId(Long notificationId);

    List<Notification> findAllByLocationId(Long locationId);

    Optional<Notification> findTopByTypeAndStatus(String type, NotificationStatusEnum status);
}

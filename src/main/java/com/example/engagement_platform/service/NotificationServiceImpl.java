package com.example.engagement_platform.service;

import com.example.engagement_platform.model.Events;
import com.example.engagement_platform.model.Notifications;
import com.example.engagement_platform.repository.EventRepository;
import com.example.engagement_platform.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotificationServiceImpl implements NotificationService{

    @Autowired
    private NotificationRepository notificationRepository;

    @Override
    public List<Notifications> getAllNotifications() {
        return notificationRepository.findAll();
    }

    @Override
    public Notifications createNotification(Notifications notifications) {
        return notificationRepository.save(notifications);
    }

    @Override
    public Notifications getNotificationById(Long notificationId) {
        Optional<Notifications> notificationFromDb = notificationRepository.findById(notificationId);
        if (notificationFromDb.isPresent()){
            return notificationFromDb.get();
        }else {
          throw new RuntimeException("Notification not Found");
        }
    }

    @Override
    public void deleteNotificationById(Long notificationId) {
        Optional<Notifications> notificationFrDb = notificationRepository.findById(notificationId);
        if (notificationFrDb.isPresent()){
            notificationRepository.deleteById(notificationId);
        }else{
            throw new RuntimeException("Notification not found");
        }
    }

    @Override
    public void updateNotificationById(Notifications notifications, Long notificationId) {
        Optional<Notifications> notificationFrDatabase = notificationRepository.findById(notificationId);
        if (notificationFrDatabase.isPresent()){
            notificationRepository.save(notifications);
        }else {
            throw new RuntimeException("Notification not found");
        }
    }

}

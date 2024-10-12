package com.example.engagement_platform.service;

import com.example.engagement_platform.model.Notification;
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
    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    @Override
    public Notification createNotification(Notification notifications) {
        return notificationRepository.save(notifications);
    }

    @Override
    public Notification getNotificationById(Long notificationId) {
        Optional<Notification> notificationFromDb = notificationRepository.findById(notificationId);
        if (notificationFromDb.isPresent()){
            return notificationFromDb.get();
        }else {
          throw new RuntimeException("Notification not Found");
        }
    }

    @Override
    public void deleteNotificationById(Long notificationId) {
        Optional<Notification> notificationFrDb = notificationRepository.findById(notificationId);
        if (notificationFrDb.isPresent()){
            notificationRepository.deleteById(notificationId);
        }else{
            throw new RuntimeException("Notification not found");
        }
    }

    @Override
    public void updateNotificationById(Notification notifications, Long notificationId) {
        Optional<Notification> notificationFrDatabase = notificationRepository.findById(notificationId);
        if (notificationFrDatabase.isPresent()){
            notificationRepository.save(notifications);
        }else {
            throw new RuntimeException("Notification not found");
        }
    }

}

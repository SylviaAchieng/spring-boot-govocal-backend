package com.example.engagement_platform.controller;

import com.example.engagement_platform.model.Notification;
import com.example.engagement_platform.service.NotificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notifications")
public class NotificationController {


    private final NotificationService notificationService;

    @GetMapping
    public ResponseEntity<List<Notification>> getAllNotifications(){
        try {
            List<Notification> notifications = notificationService.getAllNotifications();
            return new ResponseEntity<>(notifications, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<Notification> createNotification(@Valid @RequestBody Notification notifications){
        try {
            Notification createdNotification = notificationService.createNotification(notifications);
            return new ResponseEntity<>(createdNotification, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/{notificationId}")
    public ResponseEntity<Notification> getNotificationById(@PathVariable Long notificationId){
        try {
            Notification notificationById = notificationService.getNotificationById(notificationId);
            return new ResponseEntity<>(notificationById, HttpStatus.ACCEPTED);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{notificationId}")
    public ResponseEntity<Boolean> deleteNotificationById(@PathVariable Long notificationId){
        try {
            notificationService.deleteNotificationById(notificationId);
            return new ResponseEntity<>(true, HttpStatus.ACCEPTED);
        }catch (Exception e){
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{notificationId}")
    public ResponseEntity<Boolean> updateNotificationById(@Valid @RequestBody Notification notifications, @PathVariable Long notificationId){
        try {
            notificationService.updateNotificationById(notifications, notificationId);
            return new ResponseEntity<>(true, HttpStatus.ACCEPTED);
        }catch (Exception e){
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }
}

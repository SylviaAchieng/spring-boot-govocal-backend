package com.example.engagement_platform.controller;

import com.example.engagement_platform.model.Events;
import com.example.engagement_platform.model.Notifications;
import com.example.engagement_platform.service.EventService;
import com.example.engagement_platform.service.NotificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<List<Notifications>> getAllNotifications(){
        try {
            List<Notifications> notifications = notificationService.getAllNotifications();
            return new ResponseEntity<>(notifications, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<Notifications> createNotification(@Valid @RequestBody Notifications notifications){
        try {
            Notifications createdNotification = notificationService.createNotification(notifications);
            return new ResponseEntity<>(createdNotification, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/{notificationId}")
    public ResponseEntity<Notifications> getNotificationById(@PathVariable Long notificationId){
        try {
            Notifications notificationById = notificationService.getNotificationById(notificationId);
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
    public ResponseEntity<Boolean> updateNotificationById(@Valid @RequestBody Notifications notifications, @PathVariable Long notificationId){
        try {
            notificationService.updateNotificationById(notifications, notificationId);
            return new ResponseEntity<>(true, HttpStatus.ACCEPTED);
        }catch (Exception e){
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }
}

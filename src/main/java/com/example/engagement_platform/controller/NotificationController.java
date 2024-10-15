package com.example.engagement_platform.controller;

import com.example.engagement_platform.common.GenericResponseV2;
import com.example.engagement_platform.common.ResponseStatusEnum;
import com.example.engagement_platform.model.Notification;
import com.example.engagement_platform.model.dto.response.NotificationDto;
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
    public ResponseEntity<GenericResponseV2<List<NotificationDto>>> getAllNotifications(){
        GenericResponseV2<List<NotificationDto>> response = notificationService.getAllNotifications();
        if (response.getStatus().equals(ResponseStatusEnum.SUCCESS)){
            return ResponseEntity.ok().body(response);
        }else {
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping
    public ResponseEntity<GenericResponseV2<NotificationDto>> createNotification(@Valid @RequestBody NotificationDto notificationDto){
        GenericResponseV2<NotificationDto> responseV2 = notificationService.createNotification(notificationDto);
        if (responseV2.getStatus().equals(ResponseStatusEnum.SUCCESS)){
            return ResponseEntity.ok().body(responseV2);
        }else {
            return ResponseEntity.badRequest().body(responseV2);
        }

    }

    @GetMapping("/{notificationId}")
    public ResponseEntity<GenericResponseV2<NotificationDto>> getNotificationById(@PathVariable Long notificationId){
        GenericResponseV2<NotificationDto> responseV2 = notificationService.getNotificationById(notificationId);
        if (responseV2.getStatus().equals(ResponseStatusEnum.SUCCESS)){
            return ResponseEntity.ok().body(responseV2);
        }else {
            return ResponseEntity.badRequest().body(responseV2);
        }

    }

    @DeleteMapping("/{notificationId}")
    public ResponseEntity<GenericResponseV2<Boolean>> deleteNotificationById(@PathVariable Long notificationId){
        GenericResponseV2<Boolean> responseV2 = notificationService.deleteNotificationById(notificationId);
        if (responseV2.getStatus().equals(ResponseStatusEnum.SUCCESS)){
            return ResponseEntity.ok().body(responseV2);
        }else {
            return ResponseEntity.badRequest().body(responseV2);
        }
    }

    @PutMapping("/{notificationId}")
    public ResponseEntity<GenericResponseV2<Boolean>> updateNotificationById(@Valid @RequestBody NotificationDto notificationDto, @PathVariable Long notificationId){
        GenericResponseV2<Boolean> responseV2 = notificationService.updateNotificationById(notificationDto, notificationId);
        if (responseV2.getStatus().equals(ResponseStatusEnum.SUCCESS)){
            return ResponseEntity.ok().body(responseV2);
        }else {
            return ResponseEntity.badRequest().body(responseV2);
        }
    }
}

package com.example.engagement_platform.controller;

import com.example.engagement_platform.common.GenericResponseV2;
import com.example.engagement_platform.common.GenericResponseV3;
import com.example.engagement_platform.common.ResponseStatusEnum;
import com.example.engagement_platform.model.dto.response.NotificationDto;
import com.example.engagement_platform.model.dto.response.NotificationStats;
import com.example.engagement_platform.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notifications")
public class NotificationController {


    private final NotificationService notificationService;

    @Operation(summary = "get all notifications")
    @GetMapping
    public ResponseEntity<GenericResponseV2<List<NotificationDto>>> getAllNotifications(){
        GenericResponseV2<List<NotificationDto>> response = notificationService.getAllNotifications();
        if (response.getStatus().equals(ResponseStatusEnum.SUCCESS)){
            return ResponseEntity.ok().body(response);
        }else {
            return ResponseEntity.badRequest().body(response);
        }
    }
    @Operation(summary = "create notification")
    @PostMapping
    public ResponseEntity<GenericResponseV2<NotificationDto>> createNotification(@Valid @RequestBody NotificationDto notificationDto){
        GenericResponseV2<NotificationDto> responseV2 = notificationService.createNotification(notificationDto);
        if (responseV2.getStatus().equals(ResponseStatusEnum.SUCCESS)){
            return ResponseEntity.ok().body(responseV2);
        }else {
            return ResponseEntity.badRequest().body(responseV2);
        }

    }
    @Operation(summary = "get notifications by id")
    @GetMapping("/{notificationId}")
    public ResponseEntity<GenericResponseV2<NotificationDto>> getNotificationById(@PathVariable Long notificationId){
        GenericResponseV2<NotificationDto> responseV2 = notificationService.getNotificationById(notificationId);
        if (responseV2.getStatus().equals(ResponseStatusEnum.SUCCESS)){
            return ResponseEntity.ok().body(responseV2);
        }else {
            return ResponseEntity.badRequest().body(responseV2);
        }

    }
    @Operation(summary = "delete notification by id")
    @DeleteMapping("/{notificationId}")
    public ResponseEntity<GenericResponseV2<Boolean>> deleteNotificationById(@PathVariable Long notificationId){
        GenericResponseV2<Boolean> responseV2 = notificationService.deleteNotificationById(notificationId);
        if (responseV2.getStatus().equals(ResponseStatusEnum.SUCCESS)){
            return ResponseEntity.ok().body(responseV2);
        }else {
            return ResponseEntity.badRequest().body(responseV2);
        }
    }

    @Operation(summary = "update notification by id")
    @PutMapping("/{notificationId}")
    public ResponseEntity<GenericResponseV2<Boolean>> updateNotificationById(@Valid @RequestBody NotificationDto notificationDto, @PathVariable Long notificationId){
        GenericResponseV2<Boolean> responseV2 = notificationService.updateNotificationById(notificationDto, notificationId);
        if (responseV2.getStatus().equals(ResponseStatusEnum.SUCCESS)){
            return ResponseEntity.ok().body(responseV2);
        }else {
            return ResponseEntity.badRequest().body(responseV2);
        }
    }

    @GetMapping("/location/{locationId}")
    public ResponseEntity<GenericResponseV3<List<NotificationDto>, NotificationStats>> getAllNotificationsByLocationId(@PathVariable Long locationId) {
        GenericResponseV3<List<NotificationDto>, NotificationStats> response = notificationService.getAllNotificationsByLocationId(locationId);
        if (response.getStatus().equals(ResponseStatusEnum.SUCCESS)) {
            return ResponseEntity.ok().body(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }
}

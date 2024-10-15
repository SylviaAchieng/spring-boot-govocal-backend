package com.example.engagement_platform.service;

import com.example.engagement_platform.common.GenericResponseV2;
import com.example.engagement_platform.common.ResponseStatusEnum;
import com.example.engagement_platform.mappers.NotificationMapper;
import com.example.engagement_platform.model.Notification;
import com.example.engagement_platform.model.dto.response.NotificationDto;
import com.example.engagement_platform.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService{

    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;

    @Override
    public GenericResponseV2<List<NotificationDto>> getAllNotifications() {
        try {
            List<Notification> notifications =  notificationRepository.findAll();
            List<NotificationDto> response = notifications.stream().map(notificationMapper::toDto).toList();
            return GenericResponseV2.<List<NotificationDto>>builder()
                    .status(ResponseStatusEnum.SUCCESS)
                    .message("Notifications retrieved successfully")
                    ._embedded(response)
                    .build();
        }catch (Exception e){
            e.printStackTrace();
            return GenericResponseV2.<List<NotificationDto>>builder()
                    .status(ResponseStatusEnum.ERROR)
                    .message("Unable to retrieve successfully")
                    ._embedded(null)
                    .build();
        }
    }

    @Override
    public GenericResponseV2<NotificationDto> createNotification(NotificationDto notificationDto) {
        try {
            Notification notificationToBeSaved = notificationMapper.toEntity(notificationDto);
            Notification savedNotification = notificationRepository.save(notificationToBeSaved);
            NotificationDto response = notificationMapper.toDto(savedNotification);
            return GenericResponseV2.<NotificationDto>builder()
                    .status(ResponseStatusEnum.SUCCESS)
                    .message("Notification created successfully")
                    ._embedded(response)
                    .build();
        }catch (Exception e){
            e.printStackTrace();
            return GenericResponseV2.<NotificationDto>builder()
                    .status(ResponseStatusEnum.ERROR)
                    .message("Unable to create notification")
                    ._embedded(null)
                    .build();
        }
    }

    @Override
    public GenericResponseV2<NotificationDto> getNotificationById(Long notificationId) {
        try {
            Notification notificationFromDb = notificationRepository.findByNotificationId(notificationId)
                    .orElseThrow(() -> new RuntimeException("Notification not found"));
            NotificationDto response = notificationMapper.toDto(notificationFromDb);
            return GenericResponseV2.<NotificationDto>builder()
                    .status(ResponseStatusEnum.SUCCESS)
                    .message("Notification retrieved successfully")
                    ._embedded(response)
                    .build();
        }catch (Exception e){
            e.printStackTrace();
            return GenericResponseV2.<NotificationDto>builder()
                    .status(ResponseStatusEnum.ERROR)
                    .message("Unable to retrieve notification")
                    ._embedded(null)
                    .build();
        }

    }

    @Override
    public GenericResponseV2<Boolean> deleteNotificationById(Long notificationId) {
        try {
            Notification notificationFromDb = notificationRepository.findByNotificationId(notificationId).orElseThrow(() -> new RuntimeException("Notification not found"));
            notificationRepository.delete(notificationFromDb);
            return GenericResponseV2.<Boolean>builder()
                    .status(ResponseStatusEnum.SUCCESS)
                    .message("Notification deleted successfully")
                    ._embedded(true)
                    .build();
        }catch (Exception e){
            e.printStackTrace();
            return GenericResponseV2.<Boolean>builder()
                    .status(ResponseStatusEnum.ERROR)
                    .message("Unable to delete notification")
                    ._embedded(null)
                    .build();
        }
    }

    @Override
    public GenericResponseV2<Boolean> updateNotificationById(NotificationDto notificationDto, Long notificationId) {
        try {
            Notification notificationToSave = notificationMapper.toEntity(notificationDto);
            Notification savedNotification = notificationRepository.save(notificationToSave);
             notificationMapper.toDto(savedNotification);
            return GenericResponseV2.<Boolean>builder()
                    .status(ResponseStatusEnum.SUCCESS)
                    .message("Notification updated successfully")
                    ._embedded(true)
                    .build();
        }catch (Exception e){
            e.printStackTrace();
            return GenericResponseV2.<Boolean>builder()
                    .status(ResponseStatusEnum.ERROR)
                    .message("Unable to update notification")
                    ._embedded(null)
                    .build();
        }
        }

}

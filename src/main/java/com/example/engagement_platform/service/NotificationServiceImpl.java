package com.example.engagement_platform.service;

import com.example.engagement_platform.common.GenericResponseV2;
import com.example.engagement_platform.common.GenericResponseV3;
import com.example.engagement_platform.common.ResponseStatusEnum;
import com.example.engagement_platform.enums.NotificationStatusEnum;
import com.example.engagement_platform.mappers.NotificationMapper;
import com.example.engagement_platform.model.Notification;
import com.example.engagement_platform.model.dto.response.NotificationDto;
import com.example.engagement_platform.model.dto.response.NotificationStats;
import com.example.engagement_platform.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService{

    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;

    @Override
    public GenericResponseV3<List<NotificationDto>, NotificationStats> getAllNotifications() {
        try {
            List<Notification> notifications =  notificationRepository.findAll();
            List<NotificationDto> response = notifications.stream().map(notificationMapper::toDto).toList();
            NotificationStats stat = getNotificationStats(response);
            return GenericResponseV3.<List<NotificationDto>, NotificationStats>builder()
                    .status(ResponseStatusEnum.SUCCESS)
                    .message("Notifications retrieved successfully")
                    ._embedded(response)
                    .metadata(stat)
                    .build();
        }catch (Exception e){
            e.printStackTrace();
            return GenericResponseV3.<List<NotificationDto>, NotificationStats>builder()
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
            notificationFromDb.setStatus(NotificationStatusEnum.READ);
            notificationRepository.save(notificationFromDb);

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

    private static NotificationStats getNotificationStats(List<NotificationDto> notifications) {
        // get the stats
        Map<NotificationStatusEnum, List<NotificationDto>> groupedByStatus = notifications
                .stream()
                .collect(Collectors.groupingBy(NotificationDto::getStatus));

        NotificationStats stat = NotificationStats.builder().statusCounts(new HashMap<>()).build();
        groupedByStatus.forEach((notificationStatusEnum, notificationDto) -> stat.getStatusCounts().put(notificationStatusEnum, notificationDto.size()));

        Arrays.stream(NotificationStatusEnum.values()) // [CREATE, COMPLETED, CANCELLED, ...]
                .forEach(notificationStatusEnum -> {
                    if (!stat.getStatusCounts().containsKey(notificationStatusEnum)) { // if status not account for, default to 0
                        stat.getStatusCounts().put(notificationStatusEnum, 0);
                    }
                });
        return stat;
    }

    @Override
    public GenericResponseV3<List<NotificationDto>, NotificationStats> getAllNotificationsByUserId(Long userId) {
        try {
            List<Notification> notifications = notificationRepository.findAllByUserId(userId);
            List<NotificationDto> response = notifications.stream().map(notificationMapper::toDto).toList();
            NotificationStats stat = getNotificationStats(response);
            return GenericResponseV3.<List<NotificationDto>, NotificationStats>builder()
                    .status(ResponseStatusEnum.SUCCESS)
                    .message("notifications retrieved successfully")
                    ._embedded(response)
                    .metadata(stat)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            return GenericResponseV3.<List<NotificationDto>, NotificationStats>builder()
                    .status(ResponseStatusEnum.ERROR)
                    .message("unable to retrieve notifications")
                    ._embedded(null)
                    .build();
        }
    }

    @Override
    public GenericResponseV3<List<NotificationDto>, NotificationStats> getAllNotificationsByLocationId(Long locationId) {
        try {
            List<Notification> notifications = notificationRepository.findAllByLocationId(locationId);
            List<NotificationDto> response = notifications.stream().map(notificationMapper::toDto).toList();
            NotificationStats stat = getNotificationStats(response);
            return GenericResponseV3.<List<NotificationDto>, NotificationStats>builder()
                    .status(ResponseStatusEnum.SUCCESS)
                    .message("notifications retrieved successfully")
                    ._embedded(response)
                    .metadata(stat)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            return GenericResponseV3.<List<NotificationDto>, NotificationStats>builder()
                    .status(ResponseStatusEnum.ERROR)
                    .message("unable to retrieve notifications")
                    ._embedded(null)
                    .build();
        }
    }

    @Override
    public GenericResponseV3<String, Void> markAllNotificationsAsRead() {
        try {
            List<Notification> notifications = notificationRepository.findByStatus(NotificationStatusEnum.SENT);

            if (notifications.isEmpty()) {
                return GenericResponseV3.<String, Void>builder()
                        .status(ResponseStatusEnum.SUCCESS)
                        .message("No new notifications to mark as read.")
                        ._embedded("No notifications updated")
                        .build();
            }

            notifications.forEach(notification -> notification.setStatus(NotificationStatusEnum.READ));
            notificationRepository.saveAll(notifications);

            return GenericResponseV3.<String, Void>builder()
                    .status(ResponseStatusEnum.SUCCESS)
                    .message("All notifications marked as read successfully")
                    ._embedded("Notifications updated successfully")
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            return GenericResponseV3.<String, Void>builder()
                    .status(ResponseStatusEnum.ERROR)
                    .message("Failed to update notifications")
                    ._embedded(null)
                    .build();
        }
    }


}

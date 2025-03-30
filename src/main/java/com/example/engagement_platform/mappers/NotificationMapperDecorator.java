package com.example.engagement_platform.mappers;

import com.example.engagement_platform.model.Notification;
import com.example.engagement_platform.model.dto.response.NotificationDto;
import com.example.engagement_platform.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class NotificationMapperDecorator implements NotificationMapper{
    @Autowired
    @Qualifier("delegate")
    private  NotificationMapper notificationMapper;

    @Autowired
    private UserRepository userRepository;
    @Override
    public NotificationDto toDto(Notification notification) {
        NotificationDto mappedDto = notificationMapper.toDto(notification);
//        User user = notification.getUser();
//        if (user != null){
//            mappedDto.setUserId(user.getUserId());
//        }
        return mappedDto;
    }

    @Override
    public Notification toEntity(NotificationDto notificationDto) {
        Notification mappedEntity = notificationMapper.toEntity(notificationDto);
//        Long userId = notificationDto.getUserId();
//        User user = userRepository.findByUserId(userId)
//                .orElse(User.builder()
//                        .userId(userId)
//                        .build());
//        mappedEntity.setUser(user);
        return mappedEntity;
    }
}

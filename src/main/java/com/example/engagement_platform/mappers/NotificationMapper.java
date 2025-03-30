package com.example.engagement_platform.mappers;

import com.example.engagement_platform.model.Notification;
import com.example.engagement_platform.model.dto.response.NotificationDto;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
@DecoratedWith(value = NotificationMapperDecorator.class)
public interface NotificationMapper {

    NotificationMapper INSTANCE = Mappers.getMapper(NotificationMapper.class);

    //@Mapping(source = "user", target = "userId", ignore = true)
    NotificationDto toDto(Notification notification);

    // @Mapping(source = "userId", target = "user", ignore = true)
    Notification toEntity(NotificationDto notificationDto);
}

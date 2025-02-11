package com.example.engagement_platform.mappers;

import com.example.engagement_platform.model.Comment;
import com.example.engagement_platform.model.Event;
import com.example.engagement_platform.model.dto.response.CommentDto;
import com.example.engagement_platform.model.dto.response.EventDto;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
@DecoratedWith(value = EventMapperDecorator.class)
public interface EventMapper {

    EventMapper INSTANCE= Mappers.getMapper(EventMapper.class);

    @Mapping(source = "user", target = "user", ignore = true)
    @Mapping(source = "location", target = "location", ignore = true)
    @Mapping(source = "image", target = "base64EncodedImage",ignore = true)
    EventDto toDto(Event event);

    @Mapping(source = "user", target = "user", ignore = true)
    @Mapping(source = "location", target = "location", ignore = true)
    @Mapping(source = "base64EncodedImage", target = "image",ignore = true)
    Event toEntity(EventDto eventDto);
}

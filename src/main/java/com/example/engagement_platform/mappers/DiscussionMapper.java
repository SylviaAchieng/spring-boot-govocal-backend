package com.example.engagement_platform.mappers;

import com.example.engagement_platform.model.Discussion;
import com.example.engagement_platform.model.dto.response.DiscussionDto;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
@DecoratedWith(value = DiscussionMapperDecorator.class)
public interface DiscussionMapper {

    DiscussionMapper INSTANCE = Mappers.getMapper(DiscussionMapper.class);

    @Mapping(source = "user", target = "user", ignore = true)
    DiscussionDto toDto(Discussion discussion);
    @Mapping(source = "user", target = "user", ignore = true)
    Discussion toEntity(DiscussionDto discussionDto);
}

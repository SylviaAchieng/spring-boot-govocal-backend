package com.example.engagement_platform.mappers;

import com.example.engagement_platform.model.Comment;
import com.example.engagement_platform.model.dto.response.CommentDto;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
@DecoratedWith(value = CommentMapperDecorator.class)
public interface CommentMapper {

    CommentMapper INSTANCE= Mappers.getMapper(CommentMapper.class);

    @Mapping(source = "user", target = "user", ignore = true)
    @Mapping(source = "discussion", target = "discussion", ignore = true)
    CommentDto toDto(Comment comment);

    @Mapping(source = "user", target = "user", ignore = true)
    @Mapping(source = "discussion", target = "discussion", ignore = true)
    Comment toEntity(CommentDto commentDto);
}

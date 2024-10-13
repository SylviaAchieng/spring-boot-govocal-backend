package com.example.engagement_platform.mappers;

import com.example.engagement_platform.model.Comment;
import com.example.engagement_platform.model.Issue;
import com.example.engagement_platform.model.dto.response.CommentDto;
import com.example.engagement_platform.model.dto.response.IssueDto;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
@DecoratedWith(value = IssueMapperDecorator.class)
public interface IssueMapper {

    IssueMapper INSTANCE= Mappers.getMapper(IssueMapper.class);

    @Mapping(source = "user", target = "userId", ignore = true)
    @Mapping(source = "issueImage", target = "image", ignore = true)
    IssueDto toDto(Issue issue);

    @Mapping(source = "userId", target = "user", ignore = true)
    @Mapping(source = "image", target = "issueImage", ignore = true)
    Issue toEntity(IssueDto issueDto);
}

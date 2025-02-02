package com.example.engagement_platform.mappers;

import com.example.engagement_platform.model.Projects;
import com.example.engagement_platform.model.dto.response.ProjectsDto;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
@DecoratedWith(ProjectMapperDecorator.class)
public interface ProjectMapper {
    @Mapping(source = "user", target = "userId", ignore = true)
    @Mapping(source = "image", target = "base64EncodedImage",ignore = true)
    ProjectsDto toDto(Projects projects);

    @Mapping(source = "userId", target = "user", ignore = true)
    @Mapping(source = "base64EncodedImage", target = "image",ignore = true)
    Projects toEntity(ProjectsDto projectsDto);
}

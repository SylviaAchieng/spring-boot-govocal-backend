package com.example.engagement_platform.mappers;

import com.example.engagement_platform.model.Project;
import com.example.engagement_platform.model.dto.response.ProjectsDto;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
@DecoratedWith(ProjectMapperDecorator.class)
public interface ProjectMapper {
    @Mapping(source = "user", target = "user", ignore = true)
    @Mapping(source = "location", target = "location", ignore = true)
    @Mapping(source = "image", target = "base64EncodedImage",ignore = true)
    ProjectsDto toDto(Project project);

    @Mapping(source = "user", target = "user", ignore = true)
    @Mapping(source = "location", target = "location", ignore = true)
    @Mapping(source = "base64EncodedImage", target = "image",ignore = true)
    Project toEntity(ProjectsDto projectsDto);
}

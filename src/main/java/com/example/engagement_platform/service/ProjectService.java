package com.example.engagement_platform.service;

import com.example.engagement_platform.common.GenericResponseV2;
import com.example.engagement_platform.model.dto.response.ProjectsDto;

import java.math.BigDecimal;
import java.util.List;

public interface ProjectService {
    GenericResponseV2<List<ProjectsDto>> getAllProjects();

    GenericResponseV2<List<ProjectsDto>> getActiveProjects();

    GenericResponseV2<ProjectsDto> createProject(ProjectsDto projectsDto);

    GenericResponseV2<ProjectsDto> getProjectById(BigDecimal projectId);

    GenericResponseV2<Boolean> deleteProjectById(BigDecimal projectId);

    GenericResponseV2<Boolean> updateEventById(ProjectsDto projectsDto, BigDecimal projectId);

    GenericResponseV2<List<ProjectsDto>> getProjectByLocationId(Long locationId);
}

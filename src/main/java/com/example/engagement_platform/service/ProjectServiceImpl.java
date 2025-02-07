package com.example.engagement_platform.service;

import com.example.engagement_platform.common.GenericResponseV2;
import com.example.engagement_platform.common.ResponseStatusEnum;
import com.example.engagement_platform.mappers.ProjectMapper;
import com.example.engagement_platform.model.Project;
import com.example.engagement_platform.model.dto.response.ProjectsDto;
import com.example.engagement_platform.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService{
    private  final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;

    @Override
    public GenericResponseV2<List<ProjectsDto>> getAllProjects() {
        try {
            List<Project> projects = projectRepository.findAll();
            List<ProjectsDto> response = projects.stream().map(projectMapper::toDto).toList();
            return GenericResponseV2.<List<ProjectsDto>>builder()
                    .status(ResponseStatusEnum.SUCCESS)
                    .message("Projects retrieve successfully")
                    ._embedded(response)
                    .build();
        }catch (Exception e){
            e.printStackTrace();
            return GenericResponseV2.<List<ProjectsDto>>builder()
                    .status(ResponseStatusEnum.ERROR)
                    .message("Unable to retrieve projects")
                    ._embedded(null)
                    .build();
        }
    }

    @Override
    public GenericResponseV2<ProjectsDto> createProject(ProjectsDto projectsDto) {
        try {
            Project projectToBeSaved = projectMapper.toEntity(projectsDto);
            Project savedProject = projectRepository.save(projectToBeSaved);
            ProjectsDto response = projectMapper.toDto(savedProject);
            return GenericResponseV2.<ProjectsDto>builder()
                    .status(ResponseStatusEnum.SUCCESS)
                    .message("Project created successfully")
                    ._embedded(response)
                    .build();

        }catch (Exception e){
            e.printStackTrace();
            return GenericResponseV2.<ProjectsDto>builder()
                    .status(ResponseStatusEnum.ERROR)
                    .message("Unable to create project")
                    ._embedded(null)
                    .build();
        }
    }

    @Override
    public GenericResponseV2<ProjectsDto> getProjectById(BigDecimal projectId) {
        try {
            Project projectFromDb = projectRepository.findByProjectId(projectId).orElseThrow(() -> new RuntimeException("Project not found"));
            ProjectsDto response = projectMapper.toDto(projectFromDb);
            return GenericResponseV2.<ProjectsDto>builder()
                    .status(ResponseStatusEnum.SUCCESS)
                    .message("Project retrieved successfully")
                    ._embedded(response)
                    .build();
        }catch (Exception e){
            e.printStackTrace();
            return GenericResponseV2.<ProjectsDto>builder()
                    .status(ResponseStatusEnum.ERROR)
                    .message("Unable to retrieve projects")
                    ._embedded(null)
                    .build();

        }
    }

    @Override
    public GenericResponseV2<Boolean> deleteProjectById(BigDecimal projectId) {
        try {
            Project projectFromDb = projectRepository.findByProjectId(projectId).orElseThrow(() -> new RuntimeException("Issue not found"));
            projectRepository.delete(projectFromDb);
            return GenericResponseV2.<Boolean>builder()
                    .status(ResponseStatusEnum.SUCCESS)
                    .message("Project deleted successfully")
                    ._embedded(true)
                    .build();
        }catch (Exception e){
            e.printStackTrace();
            return GenericResponseV2.<Boolean>builder()
                    .status(ResponseStatusEnum.ERROR)
                    .message("Unable to delete Project")
                    ._embedded(false)
                    .build();
        }
    }

    @Override
    public GenericResponseV2<Boolean> updateEventById(ProjectsDto projectsDto, BigDecimal projectId) {
        try {
            Project projectToSave = projectMapper.toEntity(projectsDto);
            Project savedProject = projectRepository.save(projectToSave);
            projectMapper.toDto(savedProject);
            return GenericResponseV2.<Boolean>builder()
                    .status(ResponseStatusEnum.SUCCESS)
                    .message("Project updated successfully")
                    ._embedded(true)
                    .build();
        }catch (Exception e){
            e.printStackTrace();
            return GenericResponseV2.<Boolean>builder()
                    .status(ResponseStatusEnum.ERROR)
                    .message("Unable to update project")
                    ._embedded(false)
                    .build();
        }
    }
}

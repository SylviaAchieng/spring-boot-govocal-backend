package com.example.engagement_platform.service;

import com.example.engagement_platform.common.GenericResponseV2;
import com.example.engagement_platform.common.ResponseStatusEnum;
import com.example.engagement_platform.mappers.ProjectMapper;
import com.example.engagement_platform.model.Location;
import com.example.engagement_platform.model.Project;
import com.example.engagement_platform.model.User;
import com.example.engagement_platform.model.dto.UserDto;
import com.example.engagement_platform.model.dto.response.LocationDto;
import com.example.engagement_platform.model.dto.response.ProjectsDto;
import com.example.engagement_platform.repository.LocationRepository;
import com.example.engagement_platform.repository.ProjectRepository;
import com.example.engagement_platform.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService{
    private  final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;
    private final UserRepository userRepository;
    private final LocationRepository locationRepository;

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
            User user = userRepository.findByUserId(projectsDto.getUser().getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
            projectsDto.setUser(UserDto.builder()
                            .userId(user.getUserId())
                    .build());

            Location location = locationRepository.findByLocationId(projectsDto.getLocation().getLocationId()).orElseThrow(() -> new RuntimeException("Location not found"));
            projectsDto.setLocation(LocationDto.builder()
                            .locationId(location.getLocationId())
                    .build());

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

    @Override
    public GenericResponseV2<List<ProjectsDto>> getProjectByLocationId(Long locationId) {
        try {
            Location location = Location.builder()
                    .locationId(locationId)
                    .build();
            List<Project> projects = projectRepository.findAllByLocation(location);
            List<ProjectsDto> response = projects.stream().map(projectMapper::toDto).toList();
            return GenericResponseV2.<List<ProjectsDto>>builder()
                    .status(ResponseStatusEnum.SUCCESS)
                    .message("Projects retrieved successfully")
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

}

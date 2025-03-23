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
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService{
    private  final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;
    private final UserRepository userRepository;
    private final LocationRepository locationRepository;

//    @Override
//    public GenericResponseV2<List<ProjectsDto>> getAllProjects() {
//        try {
//            List<Project> projects = projectRepository.findAll();
//            List<ProjectsDto> response = projects.stream().map(projectMapper::toDto).toList();
//            getRemainingDays(response)
//            return GenericResponseV2.<List<ProjectsDto>>builder()
//                    .status(ResponseStatusEnum.SUCCESS)
//                    .message("Projects retrieve successfully")
//                    ._embedded(response)
//                    .build();
//        }catch (Exception e){
//            e.printStackTrace();
//            return GenericResponseV2.<List<ProjectsDto>>builder()
//                    .status(ResponseStatusEnum.ERROR)
//                    .message("Unable to retrieve projects")
//                    ._embedded(null)
//                    .build();
//        }
//    }


    @Override
    public GenericResponseV2<List<ProjectsDto>> getAllProjects() {
        try {
            List<Project> projects = projectRepository.findAll();
            List<ProjectsDto> response = projects.stream()
                    .map(projectMapper::toDto)
                    .peek(this::setRemainingDays) // Set remaining days
                    .toList();

            return GenericResponseV2.<List<ProjectsDto>>builder()
                    .status(ResponseStatusEnum.SUCCESS)
                    .message("Projects retrieved successfully")
                    ._embedded(response)
                    .build();
        } catch (Exception e) {
            return GenericResponseV2.<List<ProjectsDto>>builder()
                    .status(ResponseStatusEnum.ERROR)
                    .message("Failed to retrieve projects: " + e.getMessage())
                    .build();
        }
    }

    private void setRemainingDays(ProjectsDto projectDto) {
        if (projectDto.getEndDate() != null) {
            LocalDate today = LocalDate.now();
            LocalDate endDate = projectDto.getEndDate();
            long remainingDays = ChronoUnit.DAYS.between(today, endDate);
            projectDto.setDaysRemaining(BigDecimal.valueOf(remainingDays > 0 ? remainingDays : 0)); // Ensure no negative days
        } else {
            projectDto.setDaysRemaining(BigDecimal.valueOf(0)); // Default to 0 if endDate is null
        }
    }

    @Override
    public GenericResponseV2<List<ProjectsDto>> getActiveProjects() {
        try {
            List<Project> projects = projectRepository.findAll();
            List<ProjectsDto> response = projects.stream()
                    .map(projectMapper::toDto)
                    .peek(this::setRemainingDays) // Set remaining days
                    .filter(project -> project.getDaysRemaining().compareTo(BigDecimal.ZERO) > 0)
                    .sorted(Comparator.comparing(ProjectsDto::getDaysRemaining).reversed())
                    .toList();

            return GenericResponseV2.<List<ProjectsDto>>builder()
                    .status(ResponseStatusEnum.SUCCESS)
                    .message("Active projects retrieved successfully")
                    ._embedded(response)
                    .build();
        } catch (Exception e) {
            return GenericResponseV2.<List<ProjectsDto>>builder()
                    .status(ResponseStatusEnum.ERROR)
                    .message("Failed to retrieve active projects: " + e.getMessage())
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
                            .county(location.getCounty())
                    .build());
            LocalDate endDate = projectsDto.getEndDate();
            projectsDto.setDaysRemaining(BigDecimal.valueOf(getRemainingDays(endDate)));
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

    public long getRemainingDays(LocalDate endDate) {
        LocalDate today = LocalDate.now(); // Get today's date
        return ChronoUnit.DAYS.between(today, endDate); // Calculate remaining days
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
    public GenericResponseV2<Boolean> updateProjectById(ProjectsDto projectsDto, BigDecimal projectId) {
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

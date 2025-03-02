package com.example.engagement_platform.service;

import com.example.engagement_platform.common.GenericResponseV2;
import com.example.engagement_platform.common.ResponseStatusEnum;
import com.example.engagement_platform.model.Project;
import com.example.engagement_platform.model.ProjectLikes;
import com.example.engagement_platform.model.User;
import com.example.engagement_platform.model.dto.response.ProjectLikesDto;
import com.example.engagement_platform.model.dto.response.RsvpDto;
import com.example.engagement_platform.repository.ProjectLikesRepository;
import com.example.engagement_platform.repository.ProjectRepository;
import com.example.engagement_platform.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectLikesServiceImpl implements ProjectLikesService {
    private final ProjectLikesRepository projectLikesRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;

    @Override
    public GenericResponseV2<ProjectLikes> createLike(ProjectLikes projectLikes) {
        try {
            User user = userRepository.findByUserId(projectLikes.getUser().getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
            Project project = projectRepository.findByProjectId(projectLikes.getProject().getProjectId()).orElseThrow(() -> new RuntimeException("Project not found"));

            // Step 3: Check if the user has already RSVPed for this event
            boolean alreadyLiked = projectLikesRepository.existsByUserAndProject(user, project);
            if (alreadyLiked) {
                 throw new RuntimeException("user already liked the project");
            }

            // Use the managed entities instead of creating new ones
            projectLikes.setUser(user);
            projectLikes.setProject(project);
            projectLikes.setCreatedAt(LocalDateTime.now());
            ProjectLikes likes = projectLikesRepository.save(projectLikes);
            return GenericResponseV2.<ProjectLikes>builder()
                    .status(ResponseStatusEnum.SUCCESS)
                    .message("like created successfully")
                    ._embedded(likes)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            return GenericResponseV2.<ProjectLikes>builder()
                    .status(ResponseStatusEnum.ERROR)
                    .message("Unable to create likes")
                    ._embedded(null)
                    .build();
        }
    }

    @Override
    public GenericResponseV2<List<ProjectLikesDto>> getLikesByProjectId(BigDecimal projectId) {
        try {
            Project project = Project.builder()
                    .projectId(projectId)
                    .build();
            List<ProjectLikesDto> likes = projectLikesRepository.findAllByProject(project)
                    .stream()
                    .map(item -> ProjectLikesDto.builder()
                            .id(item.getId())
                            .projectId(item.getProject().getProjectId())
                            .userId(item.getUser().getUserId())
                            .createdAt(LocalDateTime.now())
                            .build()).toList();
            return GenericResponseV2.<List<ProjectLikesDto>>builder()
                    .status(ResponseStatusEnum.SUCCESS)
                    .message("Likes retrieved successfully")
                    ._embedded(likes)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            return GenericResponseV2.<List<ProjectLikesDto>>builder()
                    .status(ResponseStatusEnum.ERROR)
                    .message("Unable to retrieve likes")
                    ._embedded(null)
                    .build();
        }
    }
}

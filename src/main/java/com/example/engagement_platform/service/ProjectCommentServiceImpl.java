package com.example.engagement_platform.service;

import com.example.engagement_platform.common.GenericResponseV2;
import com.example.engagement_platform.common.ResponseStatusEnum;
import com.example.engagement_platform.model.Project;
import com.example.engagement_platform.model.ProjectComments;
import com.example.engagement_platform.model.User;
import com.example.engagement_platform.model.dto.response.ProjectCommentDto;
import com.example.engagement_platform.model.dto.response.ProjectLikesDto;
import com.example.engagement_platform.repository.ProjectCommentRepository;
import com.example.engagement_platform.repository.ProjectRepository;
import com.example.engagement_platform.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectCommentServiceImpl implements ProjectCommentService{
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final ProjectCommentRepository projectCommentRepository;

    @Override
    public GenericResponseV2<ProjectComments> createComments(ProjectComments projectComments) {
        try {
            User user = userRepository.findByUserId(projectComments.getUser().getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
            Project project = projectRepository.findByProjectId(projectComments.getProject().getProjectId()).orElseThrow(() -> new RuntimeException("Project not found"));
            projectComments.setUser(user);
            projectComments.setProject(project);
            projectComments.setCreatedAt(LocalDateTime.now());

            ProjectComments comments = projectCommentRepository.save(projectComments);
            return GenericResponseV2.<ProjectComments>builder()
                    .status(ResponseStatusEnum.SUCCESS)
                    .message("Comment created successfully")
                    ._embedded(comments)
                    .build();
        }catch (Exception e){
            e.printStackTrace();
            return GenericResponseV2.<ProjectComments>builder()
                    .status(ResponseStatusEnum.ERROR)
                    .message("Unable to create comments")
                    ._embedded(null)
                    .build();

        }
    }

    @Override
    public GenericResponseV2<List<ProjectCommentDto>> getCommentsByProjectId(BigDecimal projectId) {
        try {
            Project project = Project.builder()
                    .projectId(projectId)
                    .build();
            List<ProjectCommentDto> comments = projectCommentRepository.findAllByProject(project)
                    .stream()
                    .map(item -> ProjectCommentDto.builder()
                            .id(item.getId())
                            .projectId(item.getProject().getProjectId())
                            .userId(item.getUser().getUserId())
                            .createdAt(LocalDateTime.now())
                            .comment(item.getComment())
                            .build()).toList();
            return GenericResponseV2.<List<ProjectCommentDto>>builder()
                    .status(ResponseStatusEnum.SUCCESS)
                    .message("Comments retrieved successfully")
                    ._embedded(comments)
                    .build();
        }catch (Exception e){
            e.printStackTrace();
            return GenericResponseV2.<List<ProjectCommentDto>>builder()
                    .status(ResponseStatusEnum.ERROR)
                    .message("Unable to retrieve comments")
                    ._embedded(null)
                    .build();
        }
    }
}

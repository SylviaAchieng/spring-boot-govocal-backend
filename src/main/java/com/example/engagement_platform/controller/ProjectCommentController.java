package com.example.engagement_platform.controller;

import com.example.engagement_platform.common.GenericResponseV2;
import com.example.engagement_platform.common.ResponseStatusEnum;
import com.example.engagement_platform.model.ProjectComments;
import com.example.engagement_platform.model.ProjectLikes;
import com.example.engagement_platform.model.dto.response.ProjectCommentDto;
import com.example.engagement_platform.model.dto.response.ProjectLikesDto;
import com.example.engagement_platform.service.ProjectCommentService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/project-comments")
public class ProjectCommentController {
    private final ProjectCommentService projectCommentService;

    @Operation(summary = "create project comment")
    @PostMapping
    public ResponseEntity<GenericResponseV2<ProjectComments>> createComments(@Valid @RequestBody ProjectComments projectComments){
        GenericResponseV2<ProjectComments> response = projectCommentService.createComments(projectComments);
        if (response.getStatus().equals(ResponseStatusEnum.SUCCESS)){
            return ResponseEntity.ok().body(response);
        }else {
            return ResponseEntity.badRequest().body(response);
        }
    }

    @Operation(summary = "get comments by projectId")
    @GetMapping("/{projectId}")
    public ResponseEntity<GenericResponseV2<List<ProjectCommentDto>>> getCommentsByProjectId(@PathVariable BigDecimal projectId){
        GenericResponseV2<List<ProjectCommentDto>> response = projectCommentService.getCommentsByProjectId(projectId);
        if (response.getStatus().equals(ResponseStatusEnum.SUCCESS)){
            return ResponseEntity.ok().body(response);
        }else{
            return ResponseEntity.badRequest().body(response);
        }
    }
}

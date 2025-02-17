package com.example.engagement_platform.controller;

import com.example.engagement_platform.common.GenericResponseV2;
import com.example.engagement_platform.common.ResponseStatusEnum;
import com.example.engagement_platform.model.ProjectLikes;
import com.example.engagement_platform.model.dto.response.EventDto;
import com.example.engagement_platform.model.dto.response.ProjectLikesDto;
import com.example.engagement_platform.service.ProjectLikesService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/likes")
public class ProjectLikesController {
    private final ProjectLikesService projectLikesService;

    @Operation(summary = "create project like")
    @PostMapping
    public ResponseEntity<GenericResponseV2<ProjectLikes>> createLike(@Valid @RequestBody ProjectLikes projectLikes){
        GenericResponseV2<ProjectLikes> responseV2 = projectLikesService.createLike(projectLikes);
        if (responseV2.getStatus().equals(ResponseStatusEnum.SUCCESS)){
            return ResponseEntity.ok().body(responseV2);
        }else {
            return ResponseEntity.badRequest().body(responseV2);
        }
    }

    @Operation(summary = "get likes by projectId")
    @GetMapping("/{projectId}")
    public ResponseEntity<GenericResponseV2<List<ProjectLikesDto>>> getLikesByProjectId(@PathVariable BigDecimal projectId){
        GenericResponseV2<List<ProjectLikesDto>> response = projectLikesService.getLikesByProjectId(projectId);
        if (response.getStatus().equals(ResponseStatusEnum.SUCCESS)){
            return ResponseEntity.ok().body(response);
        }else{
            return ResponseEntity.badRequest().body(response);
        }
    }
}

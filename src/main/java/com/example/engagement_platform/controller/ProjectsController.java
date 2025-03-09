package com.example.engagement_platform.controller;

import com.example.engagement_platform.common.GenericResponseV2;
import com.example.engagement_platform.common.ResponseStatusEnum;
import com.example.engagement_platform.model.dto.response.EventDto;
import com.example.engagement_platform.model.dto.response.ProjectsDto;
import com.example.engagement_platform.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/projects")
public class ProjectsController {
    private final ProjectService projectService;

    @Operation(summary = "get all projects")
    @GetMapping
    public ResponseEntity<GenericResponseV2<List<ProjectsDto>>> getAllProjects(){
        GenericResponseV2<List<ProjectsDto>> response = projectService.getAllProjects();
        if (response.getStatus().equals(ResponseStatusEnum.SUCCESS)){
            return ResponseEntity.ok().body(response);
        }else {
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/active")
    public ResponseEntity<GenericResponseV2<List<ProjectsDto>>> getActiveProjects(){
        GenericResponseV2<List<ProjectsDto>> response = projectService.getActiveProjects();
        if (response.getStatus().equals(ResponseStatusEnum.SUCCESS)){
            return ResponseEntity.ok().body(response);
        }else {
            return ResponseEntity.badRequest().body(response);
        }
    }

    @Operation(summary = "create project")
    @PostMapping
    public ResponseEntity<GenericResponseV2<ProjectsDto>> createProject(@Valid @RequestBody ProjectsDto projectsDto){
        GenericResponseV2<ProjectsDto> responseV2 = projectService.createProject(projectsDto);
        if (responseV2.getStatus().equals(ResponseStatusEnum.SUCCESS)){
            return ResponseEntity.ok().body(responseV2);
        }else {
            return ResponseEntity.ok().body(responseV2);
        }

    }

    @Operation(summary = "get project by id")
    @GetMapping("/{projectId}")
    public ResponseEntity<GenericResponseV2<ProjectsDto>> getProjectById(@PathVariable BigDecimal projectId){
        GenericResponseV2<ProjectsDto> responseV2 = projectService.getProjectById(projectId);
        if (responseV2.getStatus().equals(ResponseStatusEnum.SUCCESS)){
            return ResponseEntity.ok().body(responseV2);
        }else {
            return ResponseEntity.badRequest().body(responseV2);
        }

    }

    @Operation(summary = "delete project by id")
    @DeleteMapping("/{projectId}")
    public ResponseEntity<GenericResponseV2<Boolean>> deleteProjectById(@PathVariable BigDecimal projectId){
        GenericResponseV2<Boolean> responseV2 = projectService.deleteProjectById(projectId);
        if (responseV2.getStatus().equals(ResponseStatusEnum.SUCCESS)){
            return ResponseEntity.ok().body(responseV2);
        }else {
            return ResponseEntity.badRequest().body(responseV2);
        }
    }

    @Operation(summary = "update project by id")
    @PutMapping("/{projectId}")
    public ResponseEntity<GenericResponseV2<Boolean>> updateEventById(@Valid @RequestBody ProjectsDto projectsDto, @PathVariable BigDecimal projectId){
        GenericResponseV2<Boolean> response = projectService.updateEventById(projectsDto,projectId);
        if (response.getStatus().equals(ResponseStatusEnum.SUCCESS)){
            return ResponseEntity.ok().body(response);
        }else {
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/location/{locationId}")
    public ResponseEntity<GenericResponseV2<List<ProjectsDto>>> getProjectByLocationId(@PathVariable Long locationId){
        GenericResponseV2<List<ProjectsDto>> response = projectService.getProjectByLocationId(locationId);
        if (response.getStatus().equals(ResponseStatusEnum.SUCCESS)){
            return ResponseEntity.ok().body(response);
        }else {
            return ResponseEntity.badRequest().body(response);
        }
    }


}

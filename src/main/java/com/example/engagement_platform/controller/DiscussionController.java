package com.example.engagement_platform.controller;

import com.example.engagement_platform.common.GenericResponseV2;
import com.example.engagement_platform.common.ResponseStatusEnum;
import com.example.engagement_platform.model.Discussion;
import com.example.engagement_platform.model.dto.response.DiscussionDto;
import com.example.engagement_platform.service.DiscussionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/discussions")
public class DiscussionController {

    private final DiscussionService discussionService;

    @GetMapping
    public ResponseEntity<GenericResponseV2<List<DiscussionDto>>> getAllDiscussions(){
        GenericResponseV2<List<DiscussionDto>> responseV2 = discussionService.getAllDiscussions();
        if (responseV2.getStatus().equals(ResponseStatusEnum.SUCCESS)){
            return ResponseEntity.ok().body(responseV2);
        }else {
            return ResponseEntity.badRequest().body(responseV2);
        }
    }

    @PostMapping
    public ResponseEntity<GenericResponseV2<DiscussionDto>> createDiscussion(@Valid @RequestBody DiscussionDto discussionDto){
        GenericResponseV2<DiscussionDto> responseV2 = discussionService.createDiscussion(discussionDto);
        if (responseV2.getStatus().equals(ResponseStatusEnum.SUCCESS)){
            return ResponseEntity.ok().body(responseV2);
        }else {
            return ResponseEntity.badRequest().body(responseV2);
        }
    }

    @GetMapping("/{discussionId}")
    public ResponseEntity<GenericResponseV2<DiscussionDto>> getDiscussionById(@PathVariable Long discussionId){
        GenericResponseV2<DiscussionDto> response = discussionService.getDiscussionById(discussionId);
        if (response.getStatus().equals(ResponseStatusEnum.SUCCESS)){
            return ResponseEntity.ok().body(response);
        }else {
            return ResponseEntity.badRequest().body(response);
        }
    }

    @DeleteMapping("/{discussionId}")
    public ResponseEntity<GenericResponseV2<Boolean>> deleteDiscussionById(@PathVariable Long discussionId){
        GenericResponseV2<Boolean> responseV2 = discussionService.deleteDiscussionById(discussionId);
        if (responseV2.getStatus().equals(ResponseStatusEnum.SUCCESS)){
            return ResponseEntity.ok().body(responseV2);
        }else {
            return ResponseEntity.badRequest().body(responseV2);
        }
    }

    @PutMapping("/{discussionId}")
    public ResponseEntity<GenericResponseV2<Boolean>> updateDiscussionById(@Valid @RequestBody DiscussionDto discussionDto, @PathVariable Long discussionId){
        GenericResponseV2<Boolean> responseV2 = discussionService.updateDiscussionById(discussionDto,discussionId);
        if (responseV2.getStatus().equals(ResponseStatusEnum.SUCCESS)){
            return ResponseEntity.ok().body(responseV2);
        }else {
            return ResponseEntity.badRequest().body(responseV2);
        }

    }
}

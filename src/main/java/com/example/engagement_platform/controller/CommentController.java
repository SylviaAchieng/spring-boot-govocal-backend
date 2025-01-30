package com.example.engagement_platform.controller;

import com.example.engagement_platform.common.GenericResponseV2;
import com.example.engagement_platform.common.ResponseStatusEnum;
import com.example.engagement_platform.model.Comment;
import com.example.engagement_platform.model.dto.response.CommentDto;
import com.example.engagement_platform.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comment")
public class CommentController {

    private final CommentService commentService;

    @Operation(summary = "get all comments")
    @GetMapping
    public ResponseEntity<GenericResponseV2<List<CommentDto>>> getAllComments(){
        GenericResponseV2<List<CommentDto>> response = commentService.getAllComments();
        if (response.getStatus().equals(ResponseStatusEnum.SUCCESS)){
            return ResponseEntity.ok().body(response);
        }else {
            return ResponseEntity.ok().body(response);
        }

    }
    @Operation(summary = "create a comment")
    @PostMapping
    public ResponseEntity<GenericResponseV2<CommentDto>> createComment(@RequestBody CommentDto commentDto){
        GenericResponseV2<CommentDto> response = commentService.createComment(commentDto);
        if (response.getStatus().equals(ResponseStatusEnum.SUCCESS)){
            return ResponseEntity.ok().body(response);
        }else {
            return ResponseEntity.badRequest().body(response);
        }
    }
    @Operation(summary = "get a comment by id")
    @GetMapping("/{commentId}")
    public ResponseEntity<GenericResponseV2<CommentDto>> getCommentById(@PathVariable Long commentId){
        GenericResponseV2<CommentDto> response = commentService.getCommentById(commentId);
        if (response.getStatus().equals(ResponseStatusEnum.SUCCESS)){
            return ResponseEntity.ok().body(response);
        }else {
            return ResponseEntity.badRequest().body(response);
        }
    }
    @Operation(summary = "delete comment by id")
    @DeleteMapping("/{commentId}")
    public ResponseEntity<GenericResponseV2<Boolean>> deleteCommentById(@PathVariable Long commentId){
        GenericResponseV2<Boolean> responseV2 = commentService.deleteCommentById(commentId);
        if (responseV2.getStatus().equals(ResponseStatusEnum.SUCCESS)){
            return ResponseEntity.ok().body(responseV2);
        }else {
            return ResponseEntity.badRequest().body(responseV2);
        }
    }
    @Operation(summary = "update comment by id")
    @PutMapping("/{commentId}")
    public ResponseEntity<GenericResponseV2<Boolean>> updateCommentById(@RequestBody CommentDto commentDto, @PathVariable Long commentId){
        GenericResponseV2<Boolean> response = commentService.updateCommentById(commentId, commentDto);
        if (response.getStatus().equals(ResponseStatusEnum.SUCCESS)){
            return ResponseEntity.ok().body(response);
        }else {
            return ResponseEntity.ok().body(response);
        }
    }
}

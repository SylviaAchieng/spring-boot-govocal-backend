package com.example.engagement_platform.controller;

import com.example.engagement_platform.model.Comment;
import com.example.engagement_platform.service.CommentService;
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

    @GetMapping
    public ResponseEntity<List<Comment>> getAllComments(){
        try {
            List<Comment> comments = commentService.getAllComments();
            return new ResponseEntity<>(comments, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<Comment> createComment(@RequestBody Comment comments){
        try {
            Comment createdComment = commentService.createComment(comments);
            return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<Comment> getCommentById(@PathVariable Long commentId){
        try {
            Comment commentById = commentService.getCommentById(commentId);
            return new ResponseEntity<>(commentById, HttpStatus.ACCEPTED);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Boolean> deleteCommentById(@PathVariable Long commentId){
        try {
            commentService.deleteCommentById(commentId);
            return new ResponseEntity<>(true, HttpStatus.ACCEPTED);
        }catch (Exception e){
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<Boolean> updateCommentById(@RequestBody Comment comments, @PathVariable Long commentId){
        try {
            commentService.updateCommentById(commentId, comments);
            return new ResponseEntity<>(true, HttpStatus.ACCEPTED);
        }catch (Exception e){
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }
}

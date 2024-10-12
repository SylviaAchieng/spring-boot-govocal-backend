package com.example.engagement_platform.controller;

import com.example.engagement_platform.model.Discussion;
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
    public ResponseEntity<List<Discussion>> getAllDiscussions(){
        try {
            List<Discussion> discussions = discussionService.getAllDiscussions();
            return new ResponseEntity<>(discussions, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<Discussion> createDiscussion(@Valid @RequestBody Discussion discussions){
        try {
            Discussion createdDiscussion = discussionService.createDiscussion(discussions);
            return new ResponseEntity<>(createdDiscussion, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/{discussionId}")
    public ResponseEntity<Discussion> getDiscussionById(@PathVariable Long discussionId){
        try {
            Discussion discussionById = discussionService.getDiscussionById(discussionId);
            return new ResponseEntity<>(discussionById, HttpStatus.ACCEPTED);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{discussionId}")
    public ResponseEntity<Boolean> deleteDiscussionById(@PathVariable Long discussionId){
        try {
            discussionService.deleteDiscussionById(discussionId);
            return new ResponseEntity<>(true, HttpStatus.ACCEPTED);
        }catch (Exception e){
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{discussionId}")
    public ResponseEntity<Boolean> updateDiscussionById(@Valid @RequestBody Discussion discussions, @PathVariable Long discussionId){
        try {
            discussionService.updateDiscussionById(discussions,discussionId);
            return new ResponseEntity<>(true, HttpStatus.ACCEPTED);
        }catch (Exception e){
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }
}

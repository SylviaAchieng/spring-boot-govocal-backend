package com.example.engagement_platform.controller;

import com.example.engagement_platform.model.Discussions;
import com.example.engagement_platform.model.Events;
import com.example.engagement_platform.service.DiscussionService;
import com.example.engagement_platform.service.EventService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/discussions")
public class DiscussionController {

    @Autowired
    private DiscussionService discussionService;

    @GetMapping
    public ResponseEntity<List<Discussions>> getAllDiscussions(){
        try {
            List<Discussions> discussions = discussionService.getAllDiscussions();
            return new ResponseEntity<>(discussions, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<Discussions> createDiscussion(@Valid @RequestBody Discussions discussions){
        try {
            Discussions createdDiscussion = discussionService.createDiscussion(discussions);
            return new ResponseEntity<>(createdDiscussion, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/{discussionId}")
    public ResponseEntity<Discussions> getDiscussionById(@PathVariable Long discussionId){
        try {
            Discussions discussionById = discussionService.getDiscussionById(discussionId);
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
    public ResponseEntity<Boolean> updateDiscussionById(@Valid @RequestBody Discussions discussions, @PathVariable Long discussionId){
        try {
            discussionService.updateDiscussionById(discussions,discussionId);
            return new ResponseEntity<>(true, HttpStatus.ACCEPTED);
        }catch (Exception e){
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }
}

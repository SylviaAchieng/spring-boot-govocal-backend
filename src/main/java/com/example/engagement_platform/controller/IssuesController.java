package com.example.engagement_platform.controller;

import com.example.engagement_platform.model.Events;
import com.example.engagement_platform.model.Issues;
import com.example.engagement_platform.service.EventService;
import com.example.engagement_platform.service.IssuesService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/issues")
public class IssuesController {

    @Autowired
    private IssuesService issuesService;

    @GetMapping
    public ResponseEntity<List<Issues>> getAllIssues(){
        try {
            List<Issues> issues = issuesService.getAllIssues();
            return new ResponseEntity<>(issues, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<Issues> createIssue(@Valid @RequestBody Issues issues){
        try {
            Issues createdIssue = issuesService.createIssue(issues);
            return new ResponseEntity<>(createdIssue, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/{issueId}")
    public ResponseEntity<Issues> getIssueById(@PathVariable Long issueId){
        try {
            Issues issueById = issuesService.getIssueById(issueId);
            return new ResponseEntity<>(issueById, HttpStatus.ACCEPTED);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{issueId}")
    public ResponseEntity<Boolean> deleteIssueById(@PathVariable Long issueId){
        try {
            issuesService.deleteIssueById(issueId);
            return new ResponseEntity<>(true, HttpStatus.ACCEPTED);
        }catch (Exception e){
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{issueId}")
    public ResponseEntity<Boolean> updateIssueById(@Valid @RequestBody Issues issues, @PathVariable Long issueId){
        try {
            issuesService.updateIssueById(issues,issueId);
            return new ResponseEntity<>(true, HttpStatus.ACCEPTED);
        }catch (Exception e){
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }
}

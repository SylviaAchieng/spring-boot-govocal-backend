package com.example.engagement_platform.controller;

import com.example.engagement_platform.common.GenericResponseV2;
import com.example.engagement_platform.common.ResponseStatusEnum;
import com.example.engagement_platform.model.Events;
import com.example.engagement_platform.model.Issues;
import com.example.engagement_platform.service.EventService;
import com.example.engagement_platform.service.IssuesService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/issue")
public class IssuesController {

    private final IssuesService issuesService;

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
    public ResponseEntity<GenericResponseV2<Issues>> createIssue(@Valid @RequestBody Issues issues){
            GenericResponseV2<Issues> responseV2 = issuesService.createIssue(issues);
            if (responseV2.getStatus().equals(ResponseStatusEnum.SUCCESS)){
                return ResponseEntity.ok().body(responseV2);
            }else {
                return ResponseEntity.badRequest().body(responseV2);
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

package com.example.engagement_platform.controller;

import com.example.engagement_platform.common.GenericResponseV2;
import com.example.engagement_platform.common.ResponseStatusEnum;
import com.example.engagement_platform.model.Issue;
import com.example.engagement_platform.model.dto.response.IssueDto;
import com.example.engagement_platform.service.IssuesService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<GenericResponseV2<List<IssueDto>>> getAllIssues(){
        try {
            GenericResponseV2<List<IssueDto>> issues = issuesService.getAllIssues();
            return new ResponseEntity<>(issues, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<GenericResponseV2<IssueDto>> createIssue(@Valid @RequestBody IssueDto issueDto){
            GenericResponseV2<IssueDto> responseV2 = issuesService.createIssue(issueDto);
            if (responseV2.getStatus().equals(ResponseStatusEnum.SUCCESS)){
                return ResponseEntity.ok().body(responseV2);
            }else {
                return ResponseEntity.badRequest().body(responseV2);
            }

    }

    @GetMapping("/{issueId}")
    public ResponseEntity<GenericResponseV2<IssueDto>> getIssueById(@PathVariable Long issueId){
            GenericResponseV2<IssueDto> issueById = issuesService.getIssueById(issueId);
            if (issueById.getStatus().equals(ResponseStatusEnum.SUCCESS)){
                return ResponseEntity.ok().body(issueById);
            }else {
                return ResponseEntity.badRequest().body(issueById);
            }
    }

    @DeleteMapping("/{issueId}")
    public ResponseEntity<GenericResponseV2<Boolean>> deleteIssueById(@PathVariable Long issueId){
        GenericResponseV2<Boolean> responseV2 = issuesService.deleteIssueById(issueId);
        if (responseV2.getStatus().equals(ResponseStatusEnum.SUCCESS)){
            return ResponseEntity.ok().body(responseV2);
        }else {
            return ResponseEntity.badRequest().body(responseV2);
        }
    }

    @PutMapping("/{issueId}")
    public ResponseEntity<GenericResponseV2<Boolean>> updateIssueById(@Valid @RequestBody IssueDto issueDto, @PathVariable Long issueId){
            GenericResponseV2<Boolean> responseV2 = issuesService.updateIssueById(issueDto,issueId);
            if (responseV2.getStatus().equals(ResponseStatusEnum.SUCCESS)){
                return ResponseEntity.ok().body(responseV2);
            }else {
                return ResponseEntity.badRequest().body(responseV2);
            }
    }
}

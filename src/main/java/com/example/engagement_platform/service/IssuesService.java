package com.example.engagement_platform.service;

import com.example.engagement_platform.common.GenericResponseV2;
import com.example.engagement_platform.model.Issue;

import java.util.List;

public interface IssuesService {
    List<Issue> getAllIssues();

    Issue getIssueById(Long issueId);

    void deleteIssueById(Long issueId);

    void updateIssueById(Issue issues, Long issueId);

    GenericResponseV2<Issue> createIssue(Issue issues);
}

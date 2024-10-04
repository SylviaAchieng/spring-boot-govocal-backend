package com.example.engagement_platform.service;

import com.example.engagement_platform.common.GenericResponseV2;
import com.example.engagement_platform.model.Events;
import com.example.engagement_platform.model.Issues;

import java.util.List;

public interface IssuesService {
    List<Issues> getAllIssues();

    Issues getIssueById(Long issueId);

    void deleteIssueById(Long issueId);

    void updateIssueById(Issues issues, Long issueId);

    GenericResponseV2<Issues> createIssue(Issues issues);
}

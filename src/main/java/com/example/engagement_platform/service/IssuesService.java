package com.example.engagement_platform.service;

import com.example.engagement_platform.common.GenericResponseV2;
import com.example.engagement_platform.model.Issue;
import com.example.engagement_platform.model.dto.response.IssueDto;

import java.util.List;

public interface IssuesService {
    GenericResponseV2<List<IssueDto>> getAllIssues();

    GenericResponseV2<IssueDto> getIssueById(Long issueId);

    GenericResponseV2<Boolean> deleteIssueById(Long issueId);

    GenericResponseV2<Boolean> updateIssueById(IssueDto issueDto, Long issueId);

    GenericResponseV2<IssueDto> createIssue(IssueDto issueDto);

    GenericResponseV2<List<IssueDto>> getIssueByLocationId(Long locationId);
}

package com.example.engagement_platform.service;

import com.example.engagement_platform.common.GenericResponseV2;
import com.example.engagement_platform.common.GenericResponseV3;
import com.example.engagement_platform.enums.IssueStatusEnum;
import com.example.engagement_platform.model.dto.response.IssueDto;
import com.example.engagement_platform.model.dto.response.IssueStats;

import java.util.List;

public interface IssuesService {
    GenericResponseV2<List<IssueDto>> getAllIssues();

    GenericResponseV2<IssueDto> getIssueById(Long issueId);

    GenericResponseV2<Boolean> deleteIssueById(Long issueId);

    GenericResponseV2<Boolean> updateIssueById(IssueDto issueDto, Long issueId);

    GenericResponseV2<IssueDto> createIssue(IssueDto issueDto);

    GenericResponseV2<List<IssueDto>> getIssueByLocationId(Long locationId);

    GenericResponseV2<List<IssueDto>> getAllIssuesByStatus(IssueStatusEnum status, Long userId);

    GenericResponseV3<List<IssueDto>, IssueStats> getIssueByUserId(Long userId);
}

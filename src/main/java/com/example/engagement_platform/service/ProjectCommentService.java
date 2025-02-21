package com.example.engagement_platform.service;

import com.example.engagement_platform.common.GenericResponseV2;
import com.example.engagement_platform.model.ProjectComments;
import com.example.engagement_platform.model.dto.response.ProjectCommentDto;

import java.math.BigDecimal;
import java.util.List;

public interface ProjectCommentService {
    GenericResponseV2<ProjectComments> createComments(ProjectComments projectComments);

    GenericResponseV2<List<ProjectCommentDto>> getCommentsByProjectId(BigDecimal projectId);
}

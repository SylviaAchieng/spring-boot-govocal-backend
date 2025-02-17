package com.example.engagement_platform.service;

import com.example.engagement_platform.common.GenericResponseV2;
import com.example.engagement_platform.model.ProjectLikes;
import com.example.engagement_platform.model.dto.response.ProjectLikesDto;

import java.math.BigDecimal;
import java.util.List;

public interface ProjectLikesService {
    GenericResponseV2<ProjectLikes> createLike(ProjectLikes projectLikes);

    GenericResponseV2<List<ProjectLikesDto>> getLikesByProjectId(BigDecimal projectId);
}

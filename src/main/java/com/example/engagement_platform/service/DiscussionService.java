package com.example.engagement_platform.service;

import com.example.engagement_platform.common.GenericResponseV2;
import com.example.engagement_platform.model.Discussion;
import com.example.engagement_platform.model.dto.response.DiscussionDto;

import java.util.List;

public interface DiscussionService {
    GenericResponseV2<List<DiscussionDto>> getAllDiscussions();

    GenericResponseV2<DiscussionDto> createDiscussion(DiscussionDto discussionDto);

    GenericResponseV2<DiscussionDto> getDiscussionById(Long discussionId);

    void deleteDiscussionById(Long discussionId);

    GenericResponseV2<Boolean> updateDiscussionById(DiscussionDto discussionDto, Long discussionId);
}

package com.example.engagement_platform.service;

import com.example.engagement_platform.model.Discussion;

import java.util.List;

public interface DiscussionService {
    List<Discussion> getAllDiscussions();

    Discussion createDiscussion(Discussion discussions);

    Discussion getDiscussionById(Long discussionId);

    void deleteDiscussionById(Long discussionId);

    void updateDiscussionById(Discussion discussions, Long discussionId);
}

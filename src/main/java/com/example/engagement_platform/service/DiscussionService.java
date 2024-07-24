package com.example.engagement_platform.service;

import com.example.engagement_platform.model.Discussions;
import com.example.engagement_platform.model.Events;

import java.util.List;

public interface DiscussionService {
    List<Discussions> getAllDiscussions();

    Discussions createDiscussion(Discussions discussions);

    Discussions getDiscussionById(Long discussionId);

    void deleteDiscussionById(Long discussionId);

    void updateDiscussionById(Discussions discussions, Long discussionId);
}

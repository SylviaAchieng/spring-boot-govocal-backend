package com.example.engagement_platform.repository;

import com.example.engagement_platform.model.DiscussionViews;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;

public interface DiscussionViewsRepository extends JpaRepository<DiscussionViews, BigDecimal> {
    boolean existsByDiscussionIdAndUserId(Long discussionId, Long userId);
}

package com.example.engagement_platform.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "discussion_views")
public class DiscussionViews {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private BigDecimal id;
    @Column(name = "discussion_id")
    private Long discussionId;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "viewed_at")
    private LocalDateTime viewedAt;
}

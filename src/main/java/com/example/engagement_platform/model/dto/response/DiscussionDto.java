package com.example.engagement_platform.model.dto.response;

import com.example.engagement_platform.model.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DiscussionDto {
    private Long discussionId;

    private String title;

    private Date createdAt;

    private String description;

    private Long userId;
}

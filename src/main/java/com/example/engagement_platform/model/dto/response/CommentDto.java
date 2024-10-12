package com.example.engagement_platform.model.dto.response;

import com.example.engagement_platform.model.Discussion;
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
public class CommentDto {

    private Long commentId;

    private String comment;

    private Date createdAt;

    private Long userId;

    private Long discussionId;
}

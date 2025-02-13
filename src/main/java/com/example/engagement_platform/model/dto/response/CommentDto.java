package com.example.engagement_platform.model.dto.response;

import com.example.engagement_platform.model.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {

    private Long commentId;

    private String comment;

    private LocalDate createdAt;

    private UserDto user;

    private DiscussionDto discussion;
}

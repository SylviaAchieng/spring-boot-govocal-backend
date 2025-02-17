package com.example.engagement_platform.model.dto.response;

import com.example.engagement_platform.enums.CategoriesEnum;
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
public class DiscussionDto {
    private Long discussionId;

    private String title;

    private LocalDate createdAt;

    private String description;

    private UserDto user;

    private Integer replyCount;

    private Integer viewCount;

    private CategoriesEnum category;
}

package com.example.engagement_platform.model.dto.response;


import com.example.engagement_platform.enums.IssueStatusEnum;
import com.example.engagement_platform.model.dto.UserDto;
import com.example.engagement_platform.model.dto.request.ImageDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IssueDto {
    private Long issueId;

    private String title;

    private String description;

    private IssueStatusEnum status;

    private Date createdAt;

    //private ImageDto image;

    private String base64EncodedImage;

    private LocationDto location;

    private UserDto user;
}

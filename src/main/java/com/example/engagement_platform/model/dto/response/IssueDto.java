package com.example.engagement_platform.model.dto.response;

import com.example.engagement_platform.model.Image;
import com.example.engagement_platform.model.Location;
import com.example.engagement_platform.model.User;
import com.example.engagement_platform.model.dto.request.ImageDto;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    private String status;

    private Date createdAt;

    private ImageDto image;

    private Long locationId;

    private Long userId;
}

package com.example.engagement_platform.model.dto.response;

import com.example.engagement_platform.model.Location;
import com.example.engagement_platform.model.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Date;
import java.time.LocalTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventDto {
    private Long eventId;

    private String title;

    private String description;

    private Date createdAt;

    private Date eventDate;

    private String base64EncodedImage;

    private LocalTime time;

    private Long locationId;

    private Long userId;
}

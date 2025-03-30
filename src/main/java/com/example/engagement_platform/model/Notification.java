package com.example.engagement_platform.model;

import com.example.engagement_platform.enums.NotificationStatusEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "notification")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notificationId;

    private String type;

    private String description;

    private Timestamp sentAt;

    @Enumerated(value = EnumType.STRING)
    private NotificationStatusEnum status;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "location_id")
    private Long locationId;

}

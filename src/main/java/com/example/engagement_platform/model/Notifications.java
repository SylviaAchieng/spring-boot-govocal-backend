package com.example.engagement_platform.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "notifications")
public class Notifications {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long notificationId;

    @NotNull(message = "notification type is mandatory")
    @NotBlank(message = "type field shld not be empty")
    private String type;

    @NotNull(message = " description is mandatory")
    @NotBlank(message = "Description field shld not be empty")
    private String description;

    @NotNull(message = "date is mandatory")
    @NotBlank(message = "sentAt field shld not be empty")
    private Date sentAt;

    @NotNull(message = "status  is mandatory")
    @NotBlank(message = "status field shld not be empty")
    private String status;

}

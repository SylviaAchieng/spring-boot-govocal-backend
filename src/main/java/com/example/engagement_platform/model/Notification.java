package com.example.engagement_platform.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.sql.Date;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "notification")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notificationId;

    @NotNull(message = "notification type is mandatory")
    @NotBlank(message = "type field shld not be empty")
    private String type;

    @NotNull(message = " description is mandatory")
    @NotBlank(message = "Description field shld not be empty")
    private String description;

    private LocalDate sentAt;

    @NotNull(message = "status  is mandatory")
    @NotBlank(message = "status field shld not be empty")
    private String status;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(referencedColumnName = "user_id", name = "user_id")
    private User user;

}

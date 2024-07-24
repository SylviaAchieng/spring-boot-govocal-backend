package com.example.engagement_platform.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Blob;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "issues")
public class Issues {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long issueId;

    @NotNull(message = "title is mandatory")
    @NotBlank(message = "title field shld not be empty")
    private String title;

    @NotNull(message = " description is mandatory")
    @NotBlank(message = "Description field shld not be empty")
    private String description;

    @NotNull(message = "status  is mandatory")
    @NotBlank(message = "status field shld not be empty")
    private String status;

    @NotNull(message = "creation date is mandatory")
    @NotBlank(message = "createdAt field shld not be empty")
    private String createdAt;

    private String issueImage;

    @NotNull(message = "location is mandatory")
    @NotBlank(message = "location field shld not be empty")
    private String location;

}

package com.example.engagement_platform.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Blob;
import java.sql.Date;

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
    private String description;

    @NotNull(message = "status  is mandatory")
    private String status;

    @NotNull(message = "creation date is mandatory")
    private Date createdAt;

    @ManyToOne
    @JoinColumn(name = "imageId")
    private Image image;

    private String Location;

    @ManyToOne
    @JoinColumn(referencedColumnName = "locationId", name = "location_id")
    private Location locationId;

    @ManyToOne
    @JoinColumn(referencedColumnName = "userId", name = "user_id")
    private Users user;

}

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
@Table(name = "issue")
public class Issue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long issueId;

    @NotNull(message = "title is mandatory")
    @NotBlank(message = "title field shld not be empty")
    private String title;

    @NotNull(message = " description is mandatory")
    private String description;

    private String status;

    @NotNull(message = "creation date is mandatory")
    private Date createdAt;

//    @ManyToOne
//    @JoinColumn(name = "image_id")
//    private Image issueImage;

    @Column(name = "image")
    private byte[] image;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(referencedColumnName = "location_id", name = "location_id")
    private Location location;

    @ManyToOne
    @JoinColumn(referencedColumnName = "user_id", name = "user_id")
    private User user;

}

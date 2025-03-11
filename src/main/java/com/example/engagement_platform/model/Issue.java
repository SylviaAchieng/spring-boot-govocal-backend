package com.example.engagement_platform.model;

import com.example.engagement_platform.enums.IssueStatusEnum;
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
@Table(name = "issues")
public class Issue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long issueId;

    private String title;

    private String description;

    @Enumerated(value = EnumType.STRING)
    private IssueStatusEnum status;


    private Date createdAt;

    @Column(name = "image")
    private byte[] image;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(referencedColumnName = "location_id", name = "location_id")
    private Location location;

    @ManyToOne
    @JoinColumn(referencedColumnName = "user_id", name = "user_id")
    private User user;

}

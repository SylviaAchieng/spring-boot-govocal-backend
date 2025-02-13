package com.example.engagement_platform.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigDecimal projectId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;  // Links project creator to Users

    @Column(name = "image")
    private byte[] image;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "days_remaining")
    private BigDecimal daysRemaining;

    @Column(name = "tag")
    private String tag;

//    private BigDecimal approximateCost;
//    private BigDecimal actualCost;
}

package com.example.engagement_platform.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id")
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

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(referencedColumnName = "location_id", name = "location_id")
    private Location location;

    @Column(name = "approximate_cost")
    private BigDecimal approximateCost;
    @Column(name = "actual_cost")
    private BigDecimal actualCost;

    @Column(name = "start_date")
    private LocalDate startDate;
    @Column(name = "end_date")
    private LocalDate endDate;
    @Column(name = "receipts")
    private byte[] receipts;
}

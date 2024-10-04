package com.example.engagement_platform.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "events")
public class Events {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "event_id")
    private Long eventId;

    @NotNull(message = "event title is mandatory")
    @NotBlank(message = "title field shld not be empty")
    @Column(name = "title")
    private String title;

    @NotNull(message = "event description is mandatory")
    @NotBlank(message = "Description field shld not be empty")
    @Column(name = "description")
    private String description;

    @CreationTimestamp
    @Column(name = "created_at")
    private Date createdAt;

    @NotNull(message = "event date is mandatory")
    @Column(name = "event_date")
    private Date eventDate;

    @ManyToOne
    @JoinColumn(referencedColumnName = "locationId", name = "location_id")
    private Location location;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(referencedColumnName = "userId", name = "user_id")
    private Users user;

}

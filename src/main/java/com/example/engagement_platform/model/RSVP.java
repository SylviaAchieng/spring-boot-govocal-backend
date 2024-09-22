package com.example.engagement_platform.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Entity
@Table
public class RSVP {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long rsvpID;

    @Column(name = "event_id")
    private Long events;

    @OneToOne
    @JoinColumn(referencedColumnName = "userId", name = "user_id")
    private Users users;

    private String rsvpStatus;
}

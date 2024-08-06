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

    @ManyToOne
    @JoinColumn(name = "eventId")
    private Events events;

    @ManyToOne
    @JoinColumn(name = "userId")
    private Users users;

    private String rsvpStatus;
}

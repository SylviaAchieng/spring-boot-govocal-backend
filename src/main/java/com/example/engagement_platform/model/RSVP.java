package com.example.engagement_platform.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.math.BigInteger;

@Data
@Builder
@Entity
@Table
public class RSVP {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rsvpID;
    @ManyToOne
    @JoinColumn(referencedColumnName = "event_id", name = "event_id")
    private Event event;

    @OneToOne
    @JoinColumn(referencedColumnName = "user_id", name = "user_id")
    private User user;

    private String rsvpStatus;
}

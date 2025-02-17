package com.example.engagement_platform.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@Builder
@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
public class RSVP {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rsvp_id")
    private Long rsvpID;
    @ManyToOne
    @JoinColumn(referencedColumnName = "event_id", name = "event_id")
    private Event event;

    @OneToOne
    @JoinColumn(referencedColumnName = "user_id", name = "user_id")
    private User user;

    @Column(name = "rsvp_status")
    private String rsvpStatus;
}

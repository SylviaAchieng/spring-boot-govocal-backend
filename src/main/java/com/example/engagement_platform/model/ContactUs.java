package com.example.engagement_platform.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "contact_us")
public class ContactUs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private BigDecimal id;
    @Column(name = "created_at")
    private LocalDateTime date;
    @Column(name = "message")
    private String message;
    @Column(name = "email")
    private String email;
    @Column(name = "user_type")
    @Enumerated(value = EnumType.STRING)
    private UserType userType;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(referencedColumnName = "location_id", name = "location_id")
    private Location location;
}

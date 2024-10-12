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
@Table(name = "public_servant")
@AllArgsConstructor
@NoArgsConstructor
public class PublicServant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "public_servantid")
    private Long publicServantID;

    @Column(name = "department")
    private String department;

    @Column(name = "position")
    private String position;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(referencedColumnName = "user_id", name = "user_id")
    private User user;
}

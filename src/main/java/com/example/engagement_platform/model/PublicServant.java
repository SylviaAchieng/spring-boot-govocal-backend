package com.example.engagement_platform.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Entity
@Table(name = "public_servant")
public class PublicServant {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long publicServantID;

    @OneToOne
    @JoinColumn(name = "userId")
    private Users users;

    private String department;

    private String position;
}

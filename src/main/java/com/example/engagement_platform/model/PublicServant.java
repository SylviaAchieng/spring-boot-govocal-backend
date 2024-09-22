package com.example.engagement_platform.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@Table(name = "public_servant")
@AllArgsConstructor
@NoArgsConstructor
public class PublicServant {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long publicServantID;

    private String department;

    private String position;
}

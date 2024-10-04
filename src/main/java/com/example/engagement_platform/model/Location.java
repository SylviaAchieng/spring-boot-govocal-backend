package com.example.engagement_platform.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "location")
public class Location {

    @Id
    @GeneratedValue
    private Long locationId;

    private String address;

    private String city;

    private String county;

    private String zipCode;

}

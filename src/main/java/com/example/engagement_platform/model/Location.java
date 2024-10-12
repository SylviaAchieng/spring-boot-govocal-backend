package com.example.engagement_platform.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "location")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@SequenceGenerator(name="location_seq",sequenceName="location_seq", allocationSize=1)
    @Column(name = "location_id")
    private Long locationId;

    @Column(name = "address")
    private String address;

    @Column(name = "sub_county")
    private String subCounty;

    @Column(name = "county")
    private String county;

}

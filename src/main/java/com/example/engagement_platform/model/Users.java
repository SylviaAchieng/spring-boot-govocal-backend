package com.example.engagement_platform.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class Users {

    @Id
    @GeneratedValue
    @Column(name = "userId")
    private Long userId;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "national_id")
    private Integer nationalId;

    @Column(name = "user_type")
    private UserType userType;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(referencedColumnName = "publicServantID", name = "public_servant_public_servantid")
    private PublicServant publicServant;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(referencedColumnName = "locationId", name = "location_id")
    private Location location;

}

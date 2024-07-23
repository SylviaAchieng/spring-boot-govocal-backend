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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;

    @NotNull(message = "fullName is mandatory")
    @NotBlank(message = "fullname should not be blank")
    @Size( min = 2,max = 200, message = "fullName must be between 2 and 200 characters")
    private String fullName;

    @Email(message = "Please provide a valid email address")
    @NotNull(message = "user email is mandatory")
    @NotBlank(message = "email field shld not be empty")
    private String email;

    @NotNull(message = "password is mandatory")
    @NotBlank(message = "password field shld not be empty")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,20}$",
            message = "Password must be 8-20 characters long, contain at least one digit, one lowercase letter, one uppercase letter, one special character, and have no whitespace")
    private String password;

    @NotNull(message = "nationalId is mandatory")
    @NotBlank(message = "nationalId field shld not be empty")
    private Integer nationalId;

    @NotNull(message = "userType is mandatory")
    @NotBlank(message = "userType field shld not be empty")
    private UserType userType;

    @NotNull(message = "user location is mandatory")
    @NotBlank(message = "location field shld not be empty")
    private String location;


}

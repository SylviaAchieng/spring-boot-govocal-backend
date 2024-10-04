package com.example.engagement_platform.model.dto;

import com.example.engagement_platform.model.PublicServant;
import com.example.engagement_platform.model.UserType;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    @JsonProperty(value = "userId")
    private Long userId;

    @NotNull(message = "fullName is mandatory")
    @Size( min = 2,max = 200, message = "fullName must be between 2 and 200 characters")
    private String fullName;

    @Email(message = "Please provide a valid email address")
    @NotNull(message = "user email is mandatory")
    private String email;

    @NotNull(message = "password is mandatory")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,20}$",
            message = "Password must be 8-20 characters long, contain at least one digit, one lowercase letter, one uppercase letter, one special character, and have no whitespace")
    private String password;

    @NotNull(message = "nationalId is mandatory")
    private Integer nationalId;

    @NotNull(message = "userType is mandatory")
    private UserType userType;

    @NotNull(message = "user location is mandatory")
    private String location;

    private Long publicServantId;

    private Long locationId;
}

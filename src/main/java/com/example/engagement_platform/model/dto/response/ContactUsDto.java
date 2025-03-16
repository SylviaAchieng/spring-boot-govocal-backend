package com.example.engagement_platform.model.dto.response;

import com.example.engagement_platform.model.UserType;
import com.example.engagement_platform.model.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContactUsDto {
    private BigDecimal id;
    private LocalDateTime date;
    private String message;
    private String email;
    private String fullName;
    private UserType userType;
    private LocationDto location;
}

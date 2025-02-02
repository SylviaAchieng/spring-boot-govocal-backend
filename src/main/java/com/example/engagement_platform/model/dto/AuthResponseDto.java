package com.example.engagement_platform.model.dto;

import com.example.engagement_platform.model.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AuthResponseDto {

    private String token;
    private User user;
}

package com.example.engagement_platform.service;

import com.example.engagement_platform.common.GenericResponseV2;
import com.example.engagement_platform.model.dto.UserDto;
import org.springframework.stereotype.Service;


public interface AuthenticationService {
    GenericResponseV2<?> register(UserDto request);
}

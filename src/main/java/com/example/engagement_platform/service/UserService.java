package com.example.engagement_platform.service;

import com.example.engagement_platform.common.GenericResponse;
import com.example.engagement_platform.common.GenericResponseV2;
import com.example.engagement_platform.model.dto.AuthResponseDto;
import com.example.engagement_platform.model.dto.UserDto;
import com.example.engagement_platform.model.dto.request.AuthRequest;
import jakarta.mail.MessagingException;

import java.util.List;

public interface UserService {



    GenericResponse addUsers(UserDto users);

    GenericResponseV2<UserDto> getUserById(Long userId);

    GenericResponseV2<Boolean> deleteUserById(Long userId);

    GenericResponseV2<Boolean> updateUserById(Long userId, UserDto userDto);

    GenericResponseV2<List<UserDto>> getAllUsers();

    GenericResponseV2<AuthResponseDto> authenticate(AuthRequest request);

    void activateAccount(String token) throws MessagingException;

    GenericResponseV2<List<UserDto>> getUserByLocationId(Long locationId);
}

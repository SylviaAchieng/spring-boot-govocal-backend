package com.example.engagement_platform.service;

import com.example.engagement_platform.common.GenericResponse;
import com.example.engagement_platform.common.GenericResponseV2;
import com.example.engagement_platform.model.User;
import com.example.engagement_platform.model.dto.UserDto;

import java.util.List;

public interface UserService {



    GenericResponse addUsers(UserDto users);

    User getUserById(Long userId);

    void deleteUserById(Long userId);

    void updateUserById(Long userId, User users);

    GenericResponseV2<List<UserDto>> getAllUsers();
}

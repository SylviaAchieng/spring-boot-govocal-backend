package com.example.engagement_platform.service;

import com.example.engagement_platform.common.GenericResponse;
import com.example.engagement_platform.common.GenericResponseV2;
import com.example.engagement_platform.model.Users;

import java.util.List;
import java.util.Optional;

public interface UserService {



    GenericResponse addUsers(Users users);

    Users getUserById(Long userId);

    void deleteUserById(Long userId);

    void updateUserById(Long userId, Users users);

    GenericResponseV2 getAllUsers();
}

package com.example.engagement_platform.service;

import com.example.engagement_platform.common.GenericResponse;
import com.example.engagement_platform.common.GenericResponseV2;
import com.example.engagement_platform.common.ResponseStatusEnum;
import com.example.engagement_platform.mappers.UserMapper;
import com.example.engagement_platform.model.Users;
import com.example.engagement_platform.model.dto.UserDto;
import com.example.engagement_platform.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public GenericResponseV2 getAllUsers() {
        try {
            List<Users> users = userRepository.findAll();
            //List<UserDto> response = users.stream().map(userMapper::UsersToUserDto).toList();
            return GenericResponseV2.builder()
                    .status(ResponseStatusEnum.SUCCESS)
                    .message("Successfully retrieved users")
                    ._embedded(users)
                    .build();
        }catch (Exception e){
            return GenericResponseV2.builder()
                    .status(ResponseStatusEnum.ERROR)
                    .message("Unable to retrieve users")
                    ._embedded(null)
                    .build();
        }
    }

    @Override
    public GenericResponse addUsers(Users users) {
        try {
            Users createdUser = userRepository.save(users);
            return GenericResponse.builder()
                    .status(ResponseStatusEnum.SUCCESS)
                    .message("Successfully added a new user")
                    ._embedded(Collections.singletonList(createdUser))
                    .build();
        }catch (Exception e){
            e.printStackTrace();
            return GenericResponse.builder()
                    .status(ResponseStatusEnum.ERROR)
                    .message("Unable to create a new user")
                    .build();
        }

    }

    @Override
    public Users getUserById(Long userId) {
        Optional<Users> userFromDb = userRepository.findById(userId);
        if (userFromDb.isPresent()){
            return userFromDb.get();
        }else {
            throw new RuntimeException("User Not Found");
        }
    }

    @Override
    public void deleteUserById(Long userId) {
        Optional<Users> userFromDatabase = userRepository.findById(userId);
        if (userFromDatabase.isPresent()){
            userRepository.deleteById(userId);
        }else {
            throw new RuntimeException("user not found");
        }

    }

    @Override
    public void updateUserById(Long userId, Users users) {
        Optional<Users> updateUser = userRepository.findById(userId);
        if (updateUser.isPresent()){
            userRepository.save(users);
        }else{
            throw new RuntimeException("user not found");
        }
    }


}

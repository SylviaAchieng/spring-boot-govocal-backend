package com.example.engagement_platform.service;

import com.example.engagement_platform.common.GenericResponse;
import com.example.engagement_platform.common.GenericResponseV2;
import com.example.engagement_platform.common.ResponseStatusEnum;
import com.example.engagement_platform.mappers.UserMapper;
import com.example.engagement_platform.model.Location;
import com.example.engagement_platform.model.UserType;
import com.example.engagement_platform.model.Users;
import com.example.engagement_platform.model.dto.UserDto;
import com.example.engagement_platform.repository.LocationRepository;
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
    private final LocationRepository locationRepository;

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
            // Fetch the location details based on the locationId from the Users entity
            if (users.getLocation() != null && users.getLocation().getLocationId() != null) {
                Location location = locationRepository.findById(users.getLocation().getLocationId())
                        .orElseThrow(() -> new RuntimeException("Location not found"));
                users.setLocation(location);  // Set the location in the user entity
            }
            // Determine user type and set relevant details
            String userType = String.valueOf(users.getUserType()).toUpperCase();

            if ("PUBLIC_SERVANT".equals(userType)) {
                // Ensure public servant details are provided
                if (users.getPublicServant() == null) {
                    return GenericResponse.builder()
                            .status(ResponseStatusEnum.ERROR)
                            .message("Public servant details must be provided for PUBLIC_SERVANT user type")
                            .build();
                }
            } else if ("ADMIN".equals(userType)) {
                users.setUserType(UserType.ADMIN);
            } else if ("CITIZEN".equals(userType)) {
                users.setUserType(UserType.CITIZEN);
            } else {
                return GenericResponse.builder()
                        .status(ResponseStatusEnum.ERROR)
                        .message("Invalid user type provided")
                        .build();
            }

            users.setUserType(UserType.PUBLIC_SERVANT);

            // Remove public servant details if user is not a public servant
            if (!"PUBLIC_SERVANT".equals(userType)) {
                users.setPublicServant(null);
            }

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

package com.example.engagement_platform.service;

import com.example.engagement_platform.common.GenericResponse;
import com.example.engagement_platform.common.GenericResponseV2;
import com.example.engagement_platform.common.ResponseStatusEnum;
import com.example.engagement_platform.mappers.UserMapper;
import com.example.engagement_platform.model.Location;
import com.example.engagement_platform.model.PublicServant;
import com.example.engagement_platform.model.User;
import com.example.engagement_platform.model.UserType;
import com.example.engagement_platform.model.dto.UserDto;
import com.example.engagement_platform.model.dto.request.PublicServantDto;
import com.example.engagement_platform.repository.LocationRepository;
import com.example.engagement_platform.repository.PublicServantRepository;
import com.example.engagement_platform.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
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
    private final PublicServantRepository publicServantRepository;

    @Override
    public GenericResponseV2<List<UserDto>> getAllUsers() {
        try {
            List<UserDto> users = userRepository.findAll()
                    .stream()
                    .map(userMapper::toDto)
                    .toList();
            return GenericResponseV2.<List<UserDto>>builder()
                    .status(ResponseStatusEnum.SUCCESS)
                    .message("Successfully retrieved users")
                    ._embedded(users)
                    .build();
        }catch (Exception e){
            e.printStackTrace();
            return GenericResponseV2.<List<UserDto>>builder()
                    .status(ResponseStatusEnum.ERROR)
                    .message("Unable to retrieve users")
                    ._embedded(null)
                    .build();
        }
    }

    @Transactional
    @Override
    public GenericResponse addUsers(UserDto newUser) {
        try {
            // Fetch the location details based on the locationId from the Users entity
            locationRepository.findByLocationId(newUser.getLocationId())
                        .orElseThrow(() -> new RuntimeException("Location not found"));

            User userToSave = userMapper.toEntity(newUser);

            User createdUser = userRepository.save(userToSave);
            if (newUser.getUserType().equals(UserType.PUBLIC_SERVANT)) {

                PublicServantDto publicServant = newUser.getPublicServant();
                if (publicServant == null) {
                    return GenericResponse.builder()
                            .status(ResponseStatusEnum.ERROR)
                            .message("Public servant details must be provided for PUBLIC_SERVANT user type")
                            .build();
                } else {
                    PublicServant servant = PublicServant.builder()
                            .user(createdUser)
                            .department(publicServant.getDepartment())
                            .position(publicServant.getPosition())
                            .build();
                    servant.setUser(createdUser);
                    publicServantRepository.save(servant);
                }

            }

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
    public User getUserById(Long userId) {
        Optional<User> userFromDb = userRepository.findById(userId);
        if (userFromDb.isPresent()){
            return userFromDb.get();
        }else {
            throw new RuntimeException("User Not Found");
        }
    }

    @Override
    public void deleteUserById(Long userId) {
        Optional<User> userFromDatabase = userRepository.findById(userId);
        if (userFromDatabase.isPresent()){
            userRepository.deleteById(userId);
        }else {
            throw new RuntimeException("user not found");
        }

    }

    @Override
    public void updateUserById(Long userId, User users) {
        Optional<User> updateUser = userRepository.findById(userId);
        if (updateUser.isPresent()){
            userRepository.save(users);
        }else{
            throw new RuntimeException("user not found");
        }
    }


}

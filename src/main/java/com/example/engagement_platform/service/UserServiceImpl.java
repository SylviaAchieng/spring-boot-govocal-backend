package com.example.engagement_platform.service;

import com.example.engagement_platform.model.Users;
import com.example.engagement_platform.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Users> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Users addUsers(Users users) {
        Users createdUser = userRepository.save(users);
        return createdUser;
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
        }
    }


}

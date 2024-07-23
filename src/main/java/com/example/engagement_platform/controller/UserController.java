package com.example.engagement_platform.controller;

import com.example.engagement_platform.model.Users;
import com.example.engagement_platform.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/hello")
    public String greetings(){
        return "Hello Sylvia, Welcome to Civic";
    }

    @GetMapping
    public ResponseEntity<List<Users>> getAllUsers(){
         List<Users> listOfUsers = userService.getAllUsers();
         return new ResponseEntity<>(listOfUsers, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Users> addUsers(@Valid @RequestBody Users users){
        try {
            Users createdUser = userService.addUsers(users);
            return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Users> getUserById(@PathVariable Long userId){
        try {
            Users userById = userService.getUserById(userId);
            return new ResponseEntity<>(userById, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Boolean> deleteUserById(@PathVariable Long userId){
        try {
            userService.deleteUserById(userId);
            return new ResponseEntity<>(true, HttpStatus.ACCEPTED);
        }catch (Exception e){
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }

    }

    @PutMapping("/{userId}")
    public ResponseEntity<Boolean> updateUserById(@Valid @RequestBody Users users, @PathVariable Long userId){
        try {
            userService.updateUserById(userId, users);
            return new ResponseEntity<>(true, HttpStatus.ACCEPTED);
        }catch (Exception e){
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }

    }
}

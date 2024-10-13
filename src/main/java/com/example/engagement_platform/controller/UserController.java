package com.example.engagement_platform.controller;

import com.example.engagement_platform.common.GenericResponse;
import com.example.engagement_platform.common.GenericResponseV2;
import com.example.engagement_platform.common.ResponseStatusEnum;
import com.example.engagement_platform.model.User;
import com.example.engagement_platform.model.dto.UserDto;
import com.example.engagement_platform.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

//    @GetMapping("/hello")
//    public String greetings(){
//        return "Hello Sylvia, Welcome to Civic";
//    }

    @GetMapping
    public ResponseEntity<GenericResponseV2<List<UserDto>>> getAllUsers(){
             GenericResponseV2<List<UserDto>> responseV2 = userService.getAllUsers();
             if (responseV2.getStatus().equals(ResponseStatusEnum.SUCCESS)){
                 return ResponseEntity.ok().body(responseV2);
             }else {
                 return ResponseEntity.badRequest().body(responseV2);
             }
    }

    @PostMapping
    public ResponseEntity<GenericResponse> addUsers(@Valid @RequestBody UserDto users){
            GenericResponse response = userService.addUsers(users);
            if(response.getStatus()== ResponseStatusEnum.SUCCESS){
                return ResponseEntity.ok().body(response);
            }else {
                return ResponseEntity.badRequest().body(response);
            }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<GenericResponseV2<UserDto>> getUserById(@PathVariable Long userId){
        GenericResponseV2<UserDto> responseV2 = userService.getUserById(userId);
        if (responseV2.getStatus().equals(ResponseStatusEnum.SUCCESS)){
            return ResponseEntity.ok().body(responseV2);
        }else {
            return ResponseEntity.badRequest().body(responseV2);
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
    public ResponseEntity<GenericResponseV2<Boolean>> updateUserById(@Valid @RequestBody UserDto userDto, @PathVariable Long userId){
        GenericResponseV2<Boolean> responseV2 = userService.updateUserById(userId, userDto);
        if (responseV2.getStatus().equals(ResponseStatusEnum.SUCCESS)){
            return ResponseEntity.ok().body(responseV2);
        }else {
            return ResponseEntity.badRequest().body(responseV2);
        }


    }
}

package com.example.engagement_platform.controller;

import com.example.engagement_platform.common.GenericResponse;
import com.example.engagement_platform.common.GenericResponseV2;
import com.example.engagement_platform.common.ResponseStatusEnum;
import com.example.engagement_platform.model.Token;
import com.example.engagement_platform.model.dto.AuthResponseDto;
import com.example.engagement_platform.model.dto.UserDto;
import com.example.engagement_platform.model.dto.request.AuthRequest;
import com.example.engagement_platform.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @Operation(summary = "authorise user")
    @PostMapping("/auth")
    public ResponseEntity<GenericResponseV2<AuthResponseDto>> authenticate(@RequestBody @Valid AuthRequest request){
        GenericResponseV2<AuthResponseDto> response = userService.authenticate(request);
        if (response.getStatus().equals(ResponseStatusEnum.SUCCESS)){
            return ResponseEntity.ok().body(response);
        }else {
            return ResponseEntity.badRequest().body(response);
        }

    }

    @Operation(summary = "activate account")
    @GetMapping("/activate-account")
    public void confirm(
            @RequestParam String token
    ) throws MessagingException {
        userService.activateAccount(token);
    }

    @Operation(summary = "get all users")
    @GetMapping
    public ResponseEntity<GenericResponseV2<List<UserDto>>> getAllUsers(){
             GenericResponseV2<List<UserDto>> responseV2 = userService.getAllUsers();
             if (responseV2.getStatus().equals(ResponseStatusEnum.SUCCESS)){
                 return ResponseEntity.ok().body(responseV2);
             }else {
                 return ResponseEntity.badRequest().body(responseV2);
             }
    }

    @Operation(summary = "add a new user")
    @PostMapping
    public ResponseEntity<GenericResponse> addUsers(@Valid @RequestBody UserDto users){
            GenericResponse response = userService.addUsers(users);
            if(response.getStatus()== ResponseStatusEnum.SUCCESS){
                return ResponseEntity.ok().body(response);
            }else {
                return ResponseEntity.badRequest().body(response);
            }
    }

    @Operation(summary = "get user by id")
    @GetMapping("/{userId}")
    public ResponseEntity<GenericResponseV2<UserDto>> getUserById(@PathVariable Long userId){
        GenericResponseV2<UserDto> responseV2 = userService.getUserById(userId);
        if (responseV2.getStatus().equals(ResponseStatusEnum.SUCCESS)){
            return ResponseEntity.ok().body(responseV2);
        }else {
            return ResponseEntity.badRequest().body(responseV2);
        }


    }
    @Operation(summary = "delete user by id")
    @DeleteMapping("/{userId}")
    public ResponseEntity<GenericResponseV2<Boolean>> deleteUserById(@PathVariable Long userId){
        GenericResponseV2<Boolean> responseV2 = userService.deleteUserById(userId);
        if (responseV2.getStatus().equals(ResponseStatusEnum.SUCCESS)){
            return ResponseEntity.ok().body(responseV2);
        }else {
            return ResponseEntity.badRequest().body(responseV2);
        }
    }
    @Operation(summary = "update user by id")
    @PutMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN') or #userId == authentication.principal.userId")
    public ResponseEntity<GenericResponseV2<Boolean>> updateUserById(@Valid @RequestBody UserDto userDto, @PathVariable Long userId){
        GenericResponseV2<Boolean> responseV2 = userService.updateUserById(userId, userDto);
        if (responseV2.getStatus().equals(ResponseStatusEnum.SUCCESS)){
            return ResponseEntity.ok().body(responseV2);
        }else {
            return ResponseEntity.badRequest().body(responseV2);
        }


    }
}

package com.caominh.identity_service.controller;

import com.caominh.identity_service.dto.request.UserCreationRequest;
import com.caominh.identity_service.dto.request.UserUpdateRequest;
import com.caominh.identity_service.dto.response.ApiResponse;
import com.caominh.identity_service.dto.response.UserResponse;
import com.caominh.identity_service.entity.User;
import com.caominh.identity_service.service.UserService;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
@RequestMapping("/users")
public class UserController {

    UserService userService;

    @PostMapping
    ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreationRequest request){

        return ApiResponse.<UserResponse>builder()
                .result(userService.createRequest(request))
                .build();
    }

    @GetMapping
    ApiResponse<List<UserResponse>> getUsers() {
        return ApiResponse.<List<UserResponse>>builder()
                .result(userService.getUsers())
                .build();
    }

    @GetMapping("/{userId}")
    UserResponse getUserId(@PathVariable("userId") String userId){
        return userService.getUserId(userId);
    }

    @PutMapping("/{userId}")
    UserResponse updateUser(@PathVariable("userId") String userId, @RequestBody UserUpdateRequest request){
        return userService.updateUser(request,userId);
    }

    @DeleteMapping("/{userId}")
    String deleted(@PathVariable("userId") String userId){
        userService.delete(userId);
        return "User have been Deleted!";
    }

}

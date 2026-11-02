package com.caominh.identity_service.controller;

import com.caominh.identity_service.dto.request.UserCreationRequest;
import com.caominh.identity_service.dto.request.UserUpdateRequest;
import com.caominh.identity_service.entity.User;
import com.caominh.identity_service.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    User createUser(@RequestBody UserCreationRequest request){
        return userService.createRequest(request);
    }

    @GetMapping
    List<User> getUsers(){
        return userService.getUsers();
    }

    @GetMapping("/{userId}")
    User getUserId(@PathVariable("userId") String userId){
        return userService.getUserId(userId);
    }

    @PutMapping("/{userId}")
    User updateUser(@PathVariable("userId") String userId, @RequestBody UserUpdateRequest request){
        return userService.updateUser(request,userId);
    }

    @DeleteMapping("/{userId}")
    String deleted(@PathVariable("userId") String userId){
        userService.delete(userId);
        return "User have been Deleted!";
    }

}

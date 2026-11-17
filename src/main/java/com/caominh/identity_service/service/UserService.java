package com.caominh.identity_service.service;

import com.caominh.identity_service.dto.request.UserCreationRequest;
import com.caominh.identity_service.dto.request.UserUpdateRequest;
import com.caominh.identity_service.dto.response.ApiResponse;
import com.caominh.identity_service.dto.response.UserResponse;
import com.caominh.identity_service.entity.User;
import com.caominh.identity_service.enums.Role;
import com.caominh.identity_service.exception.AppException;
import com.caominh.identity_service.exception.ErrorCode;
import com.caominh.identity_service.mapper.UserMapper;
import com.caominh.identity_service.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class UserService {

    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;

    public UserResponse createRequest(UserCreationRequest request){

        if (userRepository.existsByUsername(request.getUsername()))
            throw new AppException(ErrorCode.USER_EXISTED);

        User user = userMapper.toUser(request);

        user.setPassword(passwordEncoder.encode(request.getPassword()));

        HashSet<String> roles= new HashSet();
        roles.add(Role.USER.name());
        user.setRoles(roles);

        return userMapper.toUserResponse(userRepository.save(user));
    }



    public List<UserResponse> getUsers() {
        return userRepository.findAll().stream().map(userMapper::toUserResponse).toList();
    }

    public UserResponse getUserId (String id){
        return userMapper.toUserResponse(userRepository.findById(id).orElseThrow(
                () -> new RuntimeException("User not found")));
    }

    public UserResponse updateUser(UserUpdateRequest request, String id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        userMapper.updateUser(user, request);

        return userMapper.toUserResponse(userRepository.save(user));
    }

    public void delete (String id){
         userRepository.deleteById(id);
    }
}


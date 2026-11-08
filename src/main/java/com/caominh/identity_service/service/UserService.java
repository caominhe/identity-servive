package com.caominh.identity_service.service;

import com.caominh.identity_service.dto.request.UserCreationRequest;
import com.caominh.identity_service.dto.request.UserUpdateRequest;
import com.caominh.identity_service.dto.response.UserResponse;
import com.caominh.identity_service.entity.User;
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

import java.util.List;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class UserService {

    UserRepository userRepository;
    UserMapper userMapper;

    public User createRequest(UserCreationRequest request){

        if (userRepository.existsByUsername(request.getUsername()))
            throw new AppException(ErrorCode.USER_EXISTED);

        User user = userMapper.toUser(request);

        PasswordEncoder passwordEncoder= new BCryptPasswordEncoder(10);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        // Lưu lại để repo tạo 1 row mới vào database ( return để thấy được kết quả việc tạo User)
        return userRepository.save(user);
    }

    public List<User> getUsers(){
        return userRepository.findAll();
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


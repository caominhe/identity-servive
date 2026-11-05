package com.caominh.identity_service.service;

import com.caominh.identity_service.dto.request.UserCreationRequest;
import com.caominh.identity_service.dto.request.UserUpdateRequest;
import com.caominh.identity_service.entity.User;
import com.caominh.identity_service.exception.AppException;
import com.caominh.identity_service.exception.ErrorCode;
import com.caominh.identity_service.mapper.UserMapper;
import com.caominh.identity_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository,
                       UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }


    public User createRequest(UserCreationRequest request){

        if (userRepository.existsByUsername(request.getUsername()))
            throw new AppException(ErrorCode.USER_EXISTED);

        User user= userMapper.toUser(request);

        // Lưu lại để repo tạo 1 row mới vào database ( return để thấy được kết quả việc tạo User)
        return userRepository.save(user);
    }

    public List<User> getUsers(){
        return userRepository.findAll();
    }

    public User getUserId (String id){
        return userRepository.findById(id).orElseThrow(
                () -> new RuntimeException("User not found"));
    }

    public User updateUser(UserUpdateRequest request, String id){
        User user = getUserId(id);

        user.setPassword(request.getPassword());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setDob(request.getDob());

        return userRepository.save(user);
    }

    public void delete (String id){
         userRepository.deleteById(id);
    }
}

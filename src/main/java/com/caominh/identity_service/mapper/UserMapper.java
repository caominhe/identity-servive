package com.caominh.identity_service.mapper;

import com.caominh.identity_service.dto.request.UserCreationRequest;
import com.caominh.identity_service.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest request);

}

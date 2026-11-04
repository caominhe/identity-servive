package com.caominh.identity_service.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    UNCATEGORIZED_EXCEPTION(999,"Uncategorized Exception!"),
    USER_EXISTED(1001, "User Existed!"),
    USERNAME_INVALID(1003, "Username must be at least 8 characters"),
    PASSWORD_INVALID(1004, "Password must be at least 8 characters")
    ;

    private int code;
    private String message;

}

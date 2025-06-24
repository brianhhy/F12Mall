package com.avgmax.global.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.avgmax.user.dto.response.UserLoginResponse;
import com.avgmax.user.exception.PasswordMismatchException;
import com.avgmax.user.exception.UserNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<UserLoginResponse> handleUserNotFound(UserNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(UserLoginResponse.of(false, null));
    }

    @ExceptionHandler(PasswordMismatchException.class)
    public ResponseEntity<UserLoginResponse> handlePasswordMismatch(PasswordMismatchException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(UserLoginResponse.of(false, null));
    }
}
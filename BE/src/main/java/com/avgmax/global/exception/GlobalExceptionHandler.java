package com.avgmax.global.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.avgmax.user.dto.response.UserLoginResponse;
import com.avgmax.user.dto.response.UserSignupResponse;

import com.avgmax.user.exception.PasswordMismatchException;
import com.avgmax.user.exception.MaxUploadSizeExceededException;
import com.avgmax.user.exception.FileUploadFailedException;
import com.avgmax.user.exception.UserSignupFailedException;
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

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<String> handleTooLarge(MaxUploadSizeExceededException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("파일 크기가 10MB를 초과했습니다.");
    }

    @ExceptionHandler(FileUploadFailedException.class)
    public ResponseEntity<String> handleFileUploadError(FileUploadFailedException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("파일 업로드 중 오류가 발생했습니다: " + ex.getMessage());
    }

    @ExceptionHandler(UserSignupFailedException.class)
    public ResponseEntity<UserSignupResponse> handleUserSignupError(UserSignupFailedException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(UserSignupResponse.of(false, ex.getMessage(), null));
    }

}
package com.avgmax.user.exception;

public class UserSignupFailedException extends RuntimeException {
    public UserSignupFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
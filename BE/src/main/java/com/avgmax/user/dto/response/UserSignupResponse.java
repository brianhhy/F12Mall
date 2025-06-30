package com.avgmax.user.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserSignupResponse {
    private boolean success;
    private String message;
    private String userId;

    public static UserSignupResponse of(Boolean success, String message, String userId){
        return UserSignupResponse.builder()
            .success(success)
            .userId(userId)
            .build();
    }
}


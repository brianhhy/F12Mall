package com.avgmax.user.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserLoginResponse {
    private boolean success;
    private String userId;

    public static UserLoginResponse of(Boolean success, String userId){
        return UserLoginResponse.builder()
            .success(success)
            .userId(userId)
            .build();
    }
}

package com.avgmax.user.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserLoginResponse {
    private boolean success;
    private String sessionId;

    public static UserLoginResponse of(Boolean success, String sessionId){
        return UserLoginResponse.builder()
            .success(success)
            .sessionId(sessionId)
            .build();
    }
}

package com.avgmax.user.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserSignupResponse {
    private boolean success;

    public static UserSignupResponse of(Boolean success){
        return UserSignupResponse.builder()
            .success(success)
            .build();
    }
}


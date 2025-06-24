package com.avgmax.user.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserLoginRequest {
    private String username;
    private String password;
}

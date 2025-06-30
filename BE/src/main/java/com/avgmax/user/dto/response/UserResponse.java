package com.avgmax.user.dto.response;

import java.math.BigDecimal;

import com.avgmax.user.domain.User;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserResponse {
    private String userId;
    private String name;
    private String email;
    private String image;
    private BigDecimal money;

    public static UserResponse from(User user) {
        return UserResponse.builder()
            .userId(user.getUserId())
            .name(user.getName())
            .email(user.getEmail())
            .image(user.getImage())
            .money(user.getMoney())
            .build();
    }
}

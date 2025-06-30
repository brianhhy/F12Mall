package com.avgmax.user.domain;

import java.util.UUID;

import com.avgmax.global.base.BaseTimeEntity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserSkill extends BaseTimeEntity {
    @Builder.Default
    private String skillId = UUID.randomUUID().toString();
    private String userId;
}

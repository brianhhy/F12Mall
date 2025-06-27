package com.avgmax.user.domain;

import com.avgmax.global.base.BaseTimeEntity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Profile extends BaseTimeEntity {
    private String userId;
    private String position;
    private String bio;
    private String github;
    private String blog; 
    private String sns;
    private String linkedin;
    private String resume;
}

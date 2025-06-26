package com.avgmax.user.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Profile {
    private String userId;
    private String position;
    private String bio;
    private String github; //linkdata
    private String blog; //linkdata
    private String sns; //linkdata
    private String linkedin; //linkdata
    private String resume;
}

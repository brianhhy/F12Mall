package com.avgmax.user.dto.request;

import java.util.List;
import com.avgmax.user.dto.data.LinkData;

import lombok.Getter;

@Getter
public class UserProfileUpdateRequest {
    private String image;
    private String username;
    private String pwd;
    private String email;
    private String name;
    private String position;
    private String bio;
    private List<String> stack;
    private String resume;
    private List<CertificationRequest> certificateUrl;
    private LinkData link;
    private List<EducationRequest> education;
    private List<CareerRequest> career;
}

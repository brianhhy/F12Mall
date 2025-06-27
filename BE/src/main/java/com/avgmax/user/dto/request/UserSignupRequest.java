package com.avgmax.user.dto.request;

import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.avgmax.user.domain.User;
import com.avgmax.user.domain.Profile;
import com.avgmax.user.dto.data.LinkData;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSignupRequest {
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

    public User toUser(BCryptPasswordEncoder encoder) {
        return User.builder()
                .name(name)
                .email(email)
                .username(username)
                .pwd(encoder.encode(pwd))
                .image(image)
                .build();
    }

    public Profile toProfile(String userId){
         return Profile.builder()
                .userId(userId)
                .position(position)
                .github(link.getGithub())
                .sns(link.getSns())
                .blog(link.getBlog())
                .linkedin(link.getLinkedin())
                .build();
    } 

}

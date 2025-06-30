package com.avgmax.user.dto.response;

import java.util.List;
import java.util.stream.Collectors;

import com.avgmax.user.dto.data.LinkData;
import com.avgmax.user.domain.User;
import com.avgmax.user.domain.Career;
import com.avgmax.user.domain.Education;
import com.avgmax.user.domain.Certification;
//import com.avgmax.user.domain.Profile;
import com.avgmax.user.domain.UserSkill;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserInformResponse {
    private String userId;
    private String name;
    private String email;
    private String username;
    private String image;
    private BigDecimal money; 
    private String position;
    private String bio;
    private LinkData link; 
    private String resume;
    private String[] stack;
    private List<EducationResponse> education;
    private List<CareerResponse> career;
    private String[] certification;

    public static UserInformResponse from(
            User user,
            // Profile profile,
            List<Career> careerList, 
            List<Education> educationList, 
            List<Certification> certificationList,
            List<UserSkill> userSkillList
        ){
            return UserInformResponse.builder()
                .userId(user.getUserId())
                .name(user.getName())
                .email(user.getEmail())
                .username(user.getUsername())
                .image(user.getImage())
                .money(user.getMoney())
                // .bio(profile.getBio())
                // .link(LinkData.of(profile))
                // .resume(profile.getResume())
                .education(
                    educationList.stream()
                        .map(EducationResponse::from)
                        .collect(Collectors.toList())
                )
                .career(
                    careerList.stream()
                        .map(CareerResponse::from)
                        .collect(Collectors.toList())
                )
                .certification(
                    certificationList.stream()
                        .map(Certification::getCertificateUrl)
                        .toArray(String[]::new)
                )
                // .stack(...)
                .build();
        }
}

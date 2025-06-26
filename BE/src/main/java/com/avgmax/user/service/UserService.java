package com.avgmax.user.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.avgmax.user.domain.Career;
import com.avgmax.user.domain.Certification;
import com.avgmax.user.domain.Education;
import com.avgmax.user.domain.SkillUser;
import com.avgmax.user.domain.User;

import com.avgmax.user.mapper.UserMapper;
import com.avgmax.user.mapper.CareerMapper;
import com.avgmax.user.mapper.EducationMapper;
import com.avgmax.user.mapper.SkillUserMapper;
import com.avgmax.user.mapper.CertificationMapper;

import com.avgmax.user.dto.response.UserInformResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper userMapper;
    // private final ProfileMapper profileMapper;
    private final CareerMapper careerMapper;
    private final EducationMapper educationMapper;
    private final CertificationMapper certificationMapper;
    private final SkillUserMapper skillUserMapper;

    public UserInformResponse getUserInform(String userId){
        User user = userMapper.selectByUserId(userId);
        //Profile profile = profileMapper.selectByUserId(userId);
        List<Career> careerList = careerMapper.selectByUserId(userId);
        List<Education> educationList = educationMapper.selectByUserId(userId);
        List<Certification> certificationList = certificationMapper.selectByUserId(userId);
        List<SkillUser> skillUserList = skillUserMapper.selectByUserId(userId);
        
        return UserInformResponse.from(user, null, careerList, educationList, certificationList, skillUserList);
    }
}

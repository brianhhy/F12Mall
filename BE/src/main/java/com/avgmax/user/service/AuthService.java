package com.avgmax.user.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import com.avgmax.user.domain.User;
import com.avgmax.user.dto.request.UserSignupRequest;
import com.avgmax.user.dto.response.UserSignupResponse;
import com.avgmax.user.exception.PasswordMismatchException;
import com.avgmax.user.exception.UserNotFoundException;
import com.avgmax.user.exception.UserSignupFailedException;

import com.avgmax.user.mapper.CareerMapper;
import com.avgmax.user.mapper.CertificationMapper;
import com.avgmax.user.mapper.EducationMapper;
import com.avgmax.user.mapper.UserMapper;
import com.avgmax.user.mapper.ProfileMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final CareerMapper careerMapper;
    private final ProfileMapper profileMapper;
    private final EducationMapper educationMapper;
    private final CertificationMapper certificationMapper;

    @Transactional
    public UserSignupResponse createUser(UserSignupRequest request) {
        try {
            User user = request.toUser(passwordEncoder);
            userMapper.insert(user);

            String userId = user.getUserId();

            request.getCareer().forEach(c -> {
                careerMapper.insert(c.toEntity(userId));
            });

            profileMapper.insert(request.toProfile(userId));

            request.getEducation().forEach(e -> {
                educationMapper.insert(e.toEntity(userId));
            });

            request.getCertificateUrl().forEach(c -> {
                certificationMapper.insert(c.toEntity(userId));
            });

            return UserSignupResponse.of(true, "회원가입 성공", userId);
        } catch (Exception e) {
            throw new UserSignupFailedException("회원가입 중 오류가 발생했습니다", e);
        }
    }

    public User login(String username, String rawPassword) {
        User user = userMapper.selectByUsername(username);

        if (user == null) {
            throw new UserNotFoundException("사용자가 존재하지 않습니다.");
        }

        if (!passwordEncoder.matches(rawPassword, user.getPwd())) {
            throw new PasswordMismatchException("비밀번호가 일치하지 않습니다.");
        }

        return user;
    }

}

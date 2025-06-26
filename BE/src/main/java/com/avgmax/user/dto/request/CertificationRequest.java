package com.avgmax.user.dto.request;

import com.avgmax.user.domain.Certification;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CertificationRequest {
    private String certificateUrl;

    public Certification toEntity(String userId) {
        return Certification.builder()
            .userId(userId)
            .certificateUrl(certificateUrl)
            .build();
    }
}

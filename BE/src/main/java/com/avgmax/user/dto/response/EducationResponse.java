package com.avgmax.user.dto.response;

import com.avgmax.user.domain.Education;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EducationResponse {
    private String schoolName;
    private String status;
    private String major;
    private String startDate;
    private String endDate;

    public static EducationResponse from(Education education) {
        return EducationResponse.builder()
            .schoolName(education.getSchoolName())
            .status(education.getStatus())
            .major(education.getMajor())
            .startDate(education.getStartDate().toString()) // 날짜 타입이면 String으로 변환
            .endDate(education.getEndDate().toString())
            .build();
    }
}

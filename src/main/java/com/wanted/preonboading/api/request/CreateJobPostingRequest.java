package com.wanted.preonboading.api.request;

import com.wanted.preonboading.domain.jobposting.JobPostingInfo;

import java.math.BigDecimal;

public record CreateJobPostingRequest(
        String positionTitle,
        Integer reward,
        String jobDescription,
        String requiredSkill,
        Long companyId
) {
    public JobPostingInfo toInfo() {
        return new JobPostingInfo(
                null,
                positionTitle,
                reward,
                jobDescription,
                requiredSkill,
                companyId
        );
    }
}

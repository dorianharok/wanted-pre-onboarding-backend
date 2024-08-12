package com.wanted.preonboading.api.request;

import com.wanted.preonboading.domain.jobposting.JobPostingInfo;

public record EditJobPostingRequest(
        String positionTitle,
        Integer reward,
        String jobDescription,
        String requiredSkill
) {
    public JobPostingInfo toInfo(Long jobPostingId) {
        return new JobPostingInfo(
                jobPostingId,
                positionTitle,
                reward,
                jobDescription,
                requiredSkill,
                null
        );
    }
}

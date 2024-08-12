package com.wanted.preonboading.api.response;

import com.wanted.preonboading.domain.jobposting.JobPostingInfo;

public record JobPostingResponse(
        Long id,
        String positionTitle,
        Integer reward,
        String requiredSkill
) {
    public static JobPostingResponse of(JobPostingInfo info) {
        return new JobPostingResponse(
                info.id(),
                info.positionTitle(),
                info.reward(),
                info.requiredSkill()
        );
    }
}

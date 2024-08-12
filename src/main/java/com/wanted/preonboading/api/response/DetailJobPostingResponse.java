package com.wanted.preonboading.api.response;

import com.wanted.preonboading.domain.jobposting.DetailJobPostingInfo;

import java.util.List;

public record DetailJobPostingResponse(
        Long id,
        String positionTitle,
        Integer reward,
        String jobDescription,
        String requiredSkill,
        List<Long> otherJobPostingIds
) {
    public static DetailJobPostingResponse of(DetailJobPostingInfo info) {
        return new DetailJobPostingResponse(
                info.info().id(),
                info.info().positionTitle(),
                info.info().reward(),
                info.info().jobDescription(),
                info.info().requiredSkill(),
                info.otherPostings()
        );
    }
}

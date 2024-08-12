package com.wanted.preonboading.domain.jobposting;

import java.util.List;

public record DetailJobPostingInfo(
        JobPostingInfo info,
        List<Long> otherPostings
) {
}

package com.wanted.preonboading.domain.jobposting;

import com.wanted.preonboading.domain.company.Company;

import java.math.BigDecimal;

public record JobPostingInfo(
        String positionTitle,
        Integer reward,
        String jobDescription,
        String requiredSkill,
        Long companyId
) {
    public JobPosting toEntity(Company company) {
        return JobPosting.builder()
                .positionTitle(positionTitle)
                .reward(reward)
                .jobDescription(jobDescription)
                .requiredSkill(requiredSkill)
                .company(company)
                .build();
    }
}

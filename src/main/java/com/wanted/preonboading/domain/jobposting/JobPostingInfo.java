package com.wanted.preonboading.domain.jobposting;

import com.wanted.preonboading.domain.company.Company;

import java.math.BigDecimal;

public record JobPostingInfo(
        Long id,
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

    public static JobPostingInfo of(JobPosting jobPosting) {
        return new JobPostingInfo(
                jobPosting.getId(),
                jobPosting.getPositionTitle(),
                jobPosting.getReward(),
                jobPosting.getJobDescription(),
                jobPosting.getRequiredSkill(),
                jobPosting.getCompany().getId()
        );
    }
}

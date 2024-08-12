package com.wanted.preonboading.domain.jobposting;

import com.wanted.preonboading.domain.company.Company;
import com.wanted.preonboading.domain.company.CompanyFinder;
import com.wanted.preonboading.repository.JobPostingRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JobPostingAppender {
    private final JobPostingRepository jobPostingRepository;
    private final CompanyFinder companyFinder;

    @Transactional
    public Long append(JobPostingInfo info) {
        Company company = companyFinder.find(info.companyId());
        JobPosting jobPosting = jobPostingRepository.save(info.toEntity(company));
        return jobPosting.getId();
    }
}

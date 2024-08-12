package com.wanted.preonboading.domain.jobposting;

import com.wanted.preonboading.repository.JobPostingRepository;
import com.wanted.preonboading.support.error.BusinessException;
import com.wanted.preonboading.support.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class JobPostingReader {
    private final JobPostingRepository jobPostingRepository;

    public List<JobPostingInfo> readJobPostings() {
        return jobPostingRepository.findAll().stream()
                .map(JobPostingInfo::of)
                .toList();
    }

    public JobPostingInfo read(Long postingId) {
        JobPosting jobPosting = jobPostingRepository.findById(postingId)
                .orElseThrow(() -> new BusinessException(ErrorCode.JOB_POSTING_NOT_FOUND));

        return JobPostingInfo.of(jobPosting);
    }

    public List<Long> getOtherPostingIds(Long companyId, Long postingId) {
        return jobPostingRepository.findAllIdsByCompanyIdAndIdNot(companyId, postingId);
    }
}

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

    public List<JobPosting> readJobPostings() {
        return jobPostingRepository.findAll();
    }

    public JobPosting read(Long postingId) {
        return jobPostingRepository.findById(postingId)
                .orElseThrow(() -> new BusinessException(ErrorCode.JOB_POSTING_NOT_FOUND));
    }

    public List<Long> getOtherPostingIds(Long companyId, Long postingId) {
        return jobPostingRepository.findAllIdsByCompanyIdAndIdNot(companyId, postingId);
    }
}

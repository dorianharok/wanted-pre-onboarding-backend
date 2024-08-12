package com.wanted.preonboading.domain.jobposting;

import com.wanted.preonboading.repository.JobPostingRepository;
import com.wanted.preonboading.support.error.BusinessException;
import com.wanted.preonboading.support.error.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JobPostingUpdater {
    private final JobPostingRepository jobPostingRepository;

    @Transactional
    public JobPostingInfo update(JobPostingInfo info) {
        JobPosting jobPosting = jobPostingRepository.findById(info.id()).orElseThrow(
                () -> new BusinessException(ErrorCode.JOB_POSTING_NOT_FOUND)
        );

        jobPosting.update(info);

        return JobPostingInfo.of(jobPosting);
    }
}

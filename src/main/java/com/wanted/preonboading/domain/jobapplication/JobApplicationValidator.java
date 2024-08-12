package com.wanted.preonboading.domain.jobapplication;

import com.wanted.preonboading.repository.JobApplicationRepository;
import com.wanted.preonboading.support.error.BusinessException;
import com.wanted.preonboading.support.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JobApplicationValidator {
    private final JobApplicationRepository jobApplicationRepository;

    public void validate(Long jobPostingId, Long userId) {
        if (jobApplicationRepository.existsByJobPostingIdAndUserId(jobPostingId, userId)) {
            throw new BusinessException(ErrorCode.ALREADY_APPLIED);
        }
    }
}

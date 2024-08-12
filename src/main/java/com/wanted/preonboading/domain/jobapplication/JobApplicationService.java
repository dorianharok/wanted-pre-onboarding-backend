package com.wanted.preonboading.domain.jobapplication;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JobApplicationService {
    private final JobApplicationValidator jobApplicationValidator;
    private final JobApplicationAppender jobApplicationAppender;

    public void apply(Long jobPostingId, Long userId) {
        jobApplicationValidator.validate(jobPostingId, userId);

        jobApplicationAppender.apply(jobPostingId, userId);
    }
}

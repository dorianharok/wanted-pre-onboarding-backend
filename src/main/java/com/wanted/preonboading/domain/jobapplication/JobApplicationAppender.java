package com.wanted.preonboading.domain.jobapplication;

import com.wanted.preonboading.domain.jobposting.JobPosting;
import com.wanted.preonboading.domain.jobposting.JobPostingReader;
import com.wanted.preonboading.domain.user.User;
import com.wanted.preonboading.domain.user.UserReader;
import com.wanted.preonboading.repository.JobApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JobApplicationAppender {
    private final JobPostingReader jobPostingReader;
    private final UserReader userReader;
    private final JobApplicationRepository jobApplicationRepository;

    public void apply(Long jobPostingId, Long userId) {
        JobPosting jobPosting = jobPostingReader.read(jobPostingId);
        User user = userReader.read(userId);

        jobApplicationRepository.save(JobApplication.of(jobPosting, user));
    }
}

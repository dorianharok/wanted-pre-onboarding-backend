package com.wanted.preonboading.repository;

import com.wanted.preonboading.domain.jobapplication.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {
    Boolean existsByJobPostingIdAndUserId(Long jobPostingId, Long userId);
}

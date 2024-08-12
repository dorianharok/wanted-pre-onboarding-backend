package com.wanted.preonboading.repository;

import com.wanted.preonboading.domain.jobposting.JobPosting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobPostingRepository extends JpaRepository<JobPosting, Long> {
}

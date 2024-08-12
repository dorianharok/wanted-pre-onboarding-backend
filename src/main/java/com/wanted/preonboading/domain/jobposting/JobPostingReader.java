package com.wanted.preonboading.domain.jobposting;

import com.wanted.preonboading.repository.JobPostingRepository;
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
}

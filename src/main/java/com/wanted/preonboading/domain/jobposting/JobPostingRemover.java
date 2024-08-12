package com.wanted.preonboading.domain.jobposting;

import com.wanted.preonboading.repository.JobPostingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JobPostingRemover {
    private final JobPostingRepository jobPostingRepository;

    public void remove(Long postingId) {
        jobPostingRepository.deleteById(postingId);
    }
}

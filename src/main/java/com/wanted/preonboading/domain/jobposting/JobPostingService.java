package com.wanted.preonboading.domain.jobposting;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JobPostingService {
    private final JobPostingAppender jobPostingAppender;
    private final JobPostingUpdater jobPostingUpdater;
    private final JobPostingRemover jobPostingRemover;
    private final JobPostingReader jobPostingReader;

    public Long createJobPosting(JobPostingInfo jobPosting) {
        return jobPostingAppender.append(jobPosting);
    }

    public JobPostingInfo editJobPosting(JobPostingInfo info) {
        return JobPostingInfo.of(jobPostingUpdater.update(info));
    }

    public void deleteJobPosting(Long postingId) {
        jobPostingRemover.remove(postingId);
    }

    public List<JobPostingInfo> getJobPostings() {
        return jobPostingReader.readJobPostings()
                .stream()
                .map(JobPostingInfo::of)
                .toList();
    }

    public DetailJobPostingInfo getJobPostingDetails(Long postingId) {
        JobPosting jobPosting = jobPostingReader.read(postingId);
        List<Long> otherPostingIds = jobPostingReader.getOtherPostingIds(jobPosting.getCompany().getId(), postingId);

        return new DetailJobPostingInfo(JobPostingInfo.of(jobPosting), otherPostingIds);
    }
}

package com.wanted.preonboading.api;

import com.wanted.preonboading.api.request.CreateJobPostingRequest;
import com.wanted.preonboading.api.request.EditJobPostingRequest;
import com.wanted.preonboading.api.response.DetailJobPostingResponse;
import com.wanted.preonboading.api.response.JobPostingResponse;
import com.wanted.preonboading.domain.jobposting.DetailJobPostingInfo;
import com.wanted.preonboading.domain.jobposting.JobPostingInfo;
import com.wanted.preonboading.domain.jobposting.JobPostingService;
import com.wanted.preonboading.support.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/job-postings")
public class JobPostingController {
    private final JobPostingService jobPostingService;

    @PostMapping
    public ApiResponse<Long> createJobPosting(@RequestBody CreateJobPostingRequest request) {
        Long id = jobPostingService.createJobPosting(request.toInfo());

        return ApiResponse.success(id);
    }

    @PutMapping("/{postingId}")
    public ApiResponse<JobPostingResponse> editJobPosting(@RequestBody EditJobPostingRequest request, @PathVariable Long postingId) {
        JobPostingInfo jobPostingInfo = jobPostingService.editJobPosting(request.toInfo(postingId));

        return ApiResponse.success(JobPostingResponse.of(jobPostingInfo));
    }

    @DeleteMapping("/{postingId}")
    public ApiResponse<Boolean> deleteJobPosting(@PathVariable Long postingId) {
        jobPostingService.deleteJobPosting(postingId);

        return ApiResponse.success(true);
    }

    @GetMapping
    public ApiResponse<List<JobPostingResponse>> getJobPostings() {
        List<JobPostingResponse> list = jobPostingService.getJobPostings()
                .stream()
                .map(JobPostingResponse::of)
                .toList();

        return ApiResponse.success(list);
    }

    @GetMapping("/{postingId}")
    public ApiResponse<DetailJobPostingResponse> getJobPostingDetails(@PathVariable Long postingId) {
        DetailJobPostingInfo info = jobPostingService.getJobPostingDetails(postingId);

        return ApiResponse.success(DetailJobPostingResponse.of(info));
    }
}

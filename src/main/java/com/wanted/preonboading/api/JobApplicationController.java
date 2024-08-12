package com.wanted.preonboading.api;

import com.wanted.preonboading.api.request.JobApplicationCreateRequest;
import com.wanted.preonboading.domain.jobapplication.JobApplicationService;
import com.wanted.preonboading.support.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/job-applications")
public class JobApplicationController {
    private final JobApplicationService jobApplicationService;

    @PostMapping
    public ApiResponse<Boolean> applyForJob(@RequestBody JobApplicationCreateRequest request) {
        jobApplicationService.apply(request.jobPostingId(), request.userId());

        return ApiResponse.success(true);
    }
}

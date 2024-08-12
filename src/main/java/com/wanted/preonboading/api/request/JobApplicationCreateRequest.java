package com.wanted.preonboading.api.request;

public record JobApplicationCreateRequest(
        Long jobPostingId,
        Long userId
) {
}

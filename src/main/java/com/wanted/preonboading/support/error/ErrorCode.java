package com.wanted.preonboading.support.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    COMPANY_NOT_FOUND(HttpStatus.BAD_REQUEST, "회사를 찾을 수 없습니다."),
    JOB_POSTING_NOT_FOUND(HttpStatus.BAD_REQUEST, "채용공고를 찾을 수 없습니다."),
    USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "유저를 찾을 수 없습니다."),
    ALREADY_APPLIED(HttpStatus.BAD_REQUEST, "해당 채용공고에 이미 지원하셨습니다.");

    private final HttpStatus status;
    private final String message;
}
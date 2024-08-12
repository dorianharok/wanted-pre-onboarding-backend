package com.wanted.preonboading.support.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    COMPANY_NOT_FOUND(HttpStatus.BAD_REQUEST, "회사를 찾을 수 없습니다.");

    private final HttpStatus status;
    private final String message;
}
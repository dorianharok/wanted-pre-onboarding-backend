package com.wanted.preonboading.support.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {

    private ResultType status;
    private String message;
    private T data;

    public static <T> ApiResponse<T> success(T data){
        return new ApiResponse<>(ResultType.SUCCESS, null, data);
    }

    public static ApiResponse<Void> error(String message){
        return new ApiResponse<>(ResultType.ERROR, message, null);
    }
}
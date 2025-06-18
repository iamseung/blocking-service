package com.example.blocking_service.exception;

import lombok.Getter;

@Getter
public class BaseException extends RuntimeException {

    private final ErrorCode errorCode;
    private final String message;

    public BaseException(ErrorCode errorCode) {
        this.message = errorCode.getMessage();
        this.errorCode = errorCode;
    }

    public BaseException(ErrorCode errorCode, String message) {
        this.message = message;
        this.errorCode = errorCode;
    }
}

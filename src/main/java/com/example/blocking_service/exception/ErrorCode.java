package com.example.blocking_service.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    DUPLICATE_USER_ID("중복된 유저입니다.", HttpStatus.BAD_REQUEST),
    NOT_EXIST_USER("존재하지 않는 유저입니다.", HttpStatus.NOT_FOUND),
    NOT_EXIST_EXTENSION("존재하지 않는 확장자입니다.", HttpStatus.NOT_FOUND),
    NOT_VALIDATE_EXTENSION("확장자 설정이 유효하지 않습니다.", HttpStatus.NOT_FOUND);

    private final String message;
    private final HttpStatus status;
}

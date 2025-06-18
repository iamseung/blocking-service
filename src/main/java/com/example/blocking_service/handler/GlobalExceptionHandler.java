package com.example.blocking_service.handler;

import com.example.blocking_service.dto.ErrorResponseDto;
import com.example.blocking_service.exception.BaseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorResponseDto> handleBaseException(BaseException ex) {
        ErrorResponseDto response = new ErrorResponseDto(ex.getErrorCode().getStatus().value(), ex.getMessage());
        return new ResponseEntity<>(response, ex.getErrorCode().getStatus());
    }
}
package com.example.blocking_service.handler;

import com.example.blocking_service.domain.response.ErrorResponse;
import com.example.blocking_service.exception.BaseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorResponse> handleBaseException(BaseException ex) {
        ErrorResponse response = new ErrorResponse(ex.getErrorCode().getStatus().value(), ex.getMessage());
        return new ResponseEntity<>(response, ex.getErrorCode().getStatus());
    }
}
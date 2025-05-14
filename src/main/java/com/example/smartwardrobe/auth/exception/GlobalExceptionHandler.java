package com.example.smartwardrobe.auth.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Map<String,Object>> handleCustom(CustomException ex) {
        Map<String, Object> error = Map.of(
                "error", Map.of(
                        "code", ex.getCode(),
                        "message", ex.getMessage()
                )
        );
        return ResponseEntity.status(ex.getStatus()).body(error);
    }
}
package com.example.smartwardrobe.auth.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomException extends RuntimeException {
    private final String code;
    private final HttpStatus status;

    public CustomException(String code, String message, HttpStatus status) {
        super(message);
        this.code = code;
        this.status = status;
    }
}
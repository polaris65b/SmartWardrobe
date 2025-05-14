package com.example.smartwardrobe.auth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TokenResponse {
    private String token;

    public static TokenResponse of(String token) {
        return new TokenResponse(token);
    }
}

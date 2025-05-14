package com.example.smartwardrobe.auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    @NotBlank(message ="사용자 명이 비어있습니다.")
    private String username;

    @NotBlank(message = "비밀번호가 비어있습니다.")
    private String password;
}

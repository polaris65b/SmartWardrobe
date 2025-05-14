package com.example.smartwardrobe.auth.controller;

import com.example.smartwardrobe.auth.dto.request.LoginRequest;
import com.example.smartwardrobe.auth.dto.request.SignUpRequest;
import com.example.smartwardrobe.auth.dto.response.SignUpResponse;
import com.example.smartwardrobe.auth.dto.response.TokenResponse;
import com.example.smartwardrobe.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<SignUpResponse> signup(@RequestBody SignUpRequest request){
        SignUpResponse response = authService.signup(request);

        return ResponseEntity.status(201).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest request){
        TokenResponse response = authService.login(request);

        return ResponseEntity.ok(response);
    }

    // 테스트를 위해 추가하였습니다.
    @PostMapping("/adminsignup")
    public ResponseEntity<SignUpResponse> adminSignup(@RequestBody SignUpRequest request) {
        SignUpResponse resp = authService.signupAdmin(request);
        return ResponseEntity.status(201).body(resp);
    }
}

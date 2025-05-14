package com.example.smartwardrobe.auth.service;

import com.example.smartwardrobe.auth.dto.request.LoginRequest;
import com.example.smartwardrobe.auth.dto.request.SignUpRequest;
import com.example.smartwardrobe.auth.dto.response.SignUpResponse;
import com.example.smartwardrobe.auth.dto.response.TokenResponse;
import com.example.smartwardrobe.auth.exception.CustomException;
import com.example.smartwardrobe.user.entity.User;
import com.example.smartwardrobe.user.entity.UserRole;
import com.example.smartwardrobe.user.repository.UserRepository;
import com.example.smartwardrobe.config.JwtUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    JwtUtil jwtUtil;

    @InjectMocks
    AuthService authService;

    @Test
    @DisplayName("회원가입 성공: ROLE_USER 사용자 생성 후 올바른 응답 반환")
    void signup_success() {
        SignUpRequest signUpReq = new SignUpRequest("user1", "pass", "nick");

        when(userRepository.existsByUsername("user1")).thenReturn(false);
        when(passwordEncoder.encode("pass")).thenReturn("encodedPass");

        User saved = User.of("user1", "encodedPass", "nick", UserRole.ROLE_USER);
        when(userRepository.save(any(User.class))).thenReturn(saved);

        SignUpResponse resp = authService.signup(signUpReq);

        assertEquals("user1", resp.getUsername());
        assertEquals("nick",  resp.getNickname());
        assertEquals(1,       resp.getRoles().size());
        assertEquals("USER",  resp.getRoles().get(0));
        verify(userRepository).save(any());
    }

    @Test
    @DisplayName("회원가입 중복: 이미 존재하는 사용자명인 경우 CustomException 발생")
    void signup_duplicate_throws() {
        SignUpRequest signUpReq = new SignUpRequest("user1", "pass", "nick");
        when(userRepository.existsByUsername("user1")).thenReturn(true);

        CustomException ex = assertThrows(
                CustomException.class,
                () -> authService.signup(signUpReq)
        );

        assertEquals("이미 가입된 사용자입니다.", ex.getMessage());
        assertEquals("USER_ALREADY_EXISTS", ex.getCode());
        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatus());
    }

    @Test
    @DisplayName("로그인 성공: 올바른 자격 증명으로 토큰 발급")
    void login_success() {
        LoginRequest loginReq = new LoginRequest("user1", "pass");

        User user = User.of("user1", "encodedPass", "nick", UserRole.ROLE_USER);
        when(userRepository.findByUsername("user1"))
                .thenReturn(Optional.of(user));
        when(passwordEncoder.matches("pass", "encodedPass")).thenReturn(true);
        when(jwtUtil.generateToken(user)).thenReturn("jwt-token");

        TokenResponse resp = authService.login(loginReq);

        assertEquals("jwt-token", resp.getToken());
    }

    @Test
    @DisplayName("로그인 실패: 잘못된 자격 증명인 경우 CustomException 발생")
    void login_invalidCredentials_throws() {
        LoginRequest loginReq = new LoginRequest("user1", "wrong");
        when(userRepository.findByUsername("user1"))
                .thenReturn(Optional.empty());

        CustomException ex = assertThrows(
                CustomException.class,
                () -> authService.login(loginReq)
        );

        assertEquals("아이디 또는 비밀번호가 올바르지 않습니다.", ex.getMessage());
        assertEquals("INVALID_CREDENTIALS", ex.getCode());
        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatus());
    }

    @Test
    @DisplayName("관리자 회원가입 성공: ROLE_ADMIN 계정 생성 후 응답 반환")
    void signupAdmin_success() {
        SignUpRequest req = new SignUpRequest("admin1","pw","nick");
        when(userRepository.existsByUsername("admin1")).thenReturn(false);
        when(passwordEncoder.encode("pw")).thenReturn("encPw");
        User saved = User.of("admin1","encPw","nick", UserRole.ROLE_ADMIN);
        when(userRepository.save(any())).thenReturn(saved);

        SignUpResponse resp = authService.signupAdmin(req);

        assertEquals("admin1", resp.getUsername());
        assertEquals("nick",   resp.getNickname());
        assertEquals(List.of("ADMIN"), resp.getRoles());
    }

    @Test
    @DisplayName("관리자 회원가입 중복: 이미 존재하는 관리자명인 경우 400 BAD_REQUEST 발생")
    void signupAdmin_duplicate_throws() {
        SignUpRequest req = new SignUpRequest("admin1", "pw", "nick");
        when(userRepository.existsByUsername("admin1")).thenReturn(true);

        ResponseStatusException ex = assertThrows(
                ResponseStatusException.class,
                () -> authService.signupAdmin(req)
        );

        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
        assertEquals("이미 존재하는 사용자입니다.", ex.getReason());
    }
}

package com.example.smartwardrobe.auth.service;


import com.example.smartwardrobe.auth.dto.request.LoginRequest;
import com.example.smartwardrobe.auth.dto.request.SignUpRequest;
import com.example.smartwardrobe.auth.dto.response.SignUpResponse;
import com.example.smartwardrobe.auth.dto.response.TokenResponse;
import com.example.smartwardrobe.auth.exception.CustomException;
import com.example.smartwardrobe.config.JwtUtil;
import com.example.smartwardrobe.user.entity.User;
import com.example.smartwardrobe.user.entity.UserRole;
import com.example.smartwardrobe.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public SignUpResponse signup(SignUpRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new CustomException(
                    "USER_ALREADY_EXISTS",
                    "이미 가입된 사용자입니다.",
                    HttpStatus.BAD_REQUEST
            );
        }
        String encoded = passwordEncoder.encode(request.getPassword());
        User user = User.of(
                request.getUsername(),
                encoded,
                request.getNickname(),
                UserRole.ROLE_USER
        );
        userRepository.save(user);
        return SignUpResponse.of(user.getUsername(), user.getNickname(), user.getRole());
    }

    public TokenResponse login(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new CustomException(
                        "INVALID_CREDENTIALS",
                        "아이디 또는 비밀번호가 올바르지 않습니다.",
                        HttpStatus.BAD_REQUEST
                ));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new CustomException(
                    "INVALID_CREDENTIALS",
                    "아이디 또는 비밀번호가 올바르지 않습니다.",
                    HttpStatus.BAD_REQUEST
            );
        }

        String token = jwtUtil.generateToken(user);

        return TokenResponse.of(token);
    }

    // 테스트를 위해 추가했습니다.
    public SignUpResponse signupAdmin(SignUpRequest req) {
        if (userRepository.existsByUsername(req.getUsername())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이미 존재하는 사용자입니다.");
        }
        String enc = passwordEncoder.encode(req.getPassword());
        User admin = User.of(req.getUsername(), enc, req.getNickname(), UserRole.ROLE_ADMIN);
        userRepository.save(admin);

        return SignUpResponse.of(admin.getUsername(), admin.getNickname(), admin.getRole());
    }
}

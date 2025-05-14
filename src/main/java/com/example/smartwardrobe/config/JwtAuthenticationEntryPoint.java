package com.example.smartwardrobe.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        Map<String,Object> error = Map.of(
                "error", Map.of(
                        "code", "INVALID_TOKEN",
                        "message", "유효하지 않은 인증 토큰입니다."
                )
        );
        try {
            mapper.writeValue(response.getWriter(), error);
        } catch (Exception ignored) { }
    }
}
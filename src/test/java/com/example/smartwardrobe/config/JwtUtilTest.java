package com.example.smartwardrobe.config;

import com.example.smartwardrobe.user.entity.User;
import com.example.smartwardrobe.user.entity.UserRole;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JwtUtilTest {

    private final JwtUtil jwtUtil = new JwtUtil();

    @Test
    @DisplayName("토큰 생성 후 validateToken이 true, claims 정상 추출")
    void generateAndValidateToken() {
        // given
        User user = User.builder()
                .id(123L)
                .username("u1")
                .password("p")
                .nickname("n")
                .role(UserRole.ROLE_USER)
                .build();

        // when
        String token = jwtUtil.generateToken(user);

        // then
        assertTrue(jwtUtil.validateToken(token), "생성된 토큰은 유효해야 한다");
        assertEquals(123L, jwtUtil.getUserIdFromToken(token), "토큰에서 ID가 올바르게 읽혀야 한다");
        assertEquals("ROLE_USER", jwtUtil.getRoleFromToken(token), "토큰에서 role이 올바르게 읽혀야 한다");
    }

    @Test
    @DisplayName("잘못된 토큰은 validateToken이 false")
    void invalidToken() {
        assertFalse(jwtUtil.validateToken("this.is.not.a.jwt"), "임의 문자열은 유효하지 않아야 한다");
    }
}

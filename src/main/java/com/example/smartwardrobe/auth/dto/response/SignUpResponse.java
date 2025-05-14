package com.example.smartwardrobe.auth.dto.response;

import com.example.smartwardrobe.user.entity.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Collections;
import java.util.List;

@Getter
@AllArgsConstructor
public class SignUpResponse {

    private String username;
    private String nickname;
    private List<String> roles;

    @Getter
    @AllArgsConstructor
    public static class RoleDto {
        private String role;
    }

    public static SignUpResponse of(String username, String nickname, UserRole role) {
        String cleaned = role.name().replace("ROLE_", "");
        return new SignUpResponse(
                username,
                nickname,
                Collections.singletonList(cleaned)
        );
    }
}

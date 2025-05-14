package com.example.smartwardrobe.auth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class RoleChangeResponse {
    private String username;
    private String nickname;
    private List<String> roles;

    public static RoleChangeResponse of(String username, String nickname, String role) {
        return new RoleChangeResponse(
                username,
                nickname,
                List.of(role.replace("ROLE_", ""))
        );
    }
}
package com.example.smartwardrobe.auth.dto.request;

import com.example.smartwardrobe.user.entity.UserRole;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RoleChangeRequest {
    private UserRole role;
}

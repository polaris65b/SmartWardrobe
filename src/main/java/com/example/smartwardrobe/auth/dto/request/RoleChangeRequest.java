package com.example.smartwardrobe.auth.dto.request;

import com.example.smartwardrobe.user.entity.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RoleChangeRequest {
    private UserRole role;
}

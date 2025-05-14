package com.example.smartwardrobe.user.controller;

import com.example.smartwardrobe.auth.dto.request.RoleChangeRequest;
import com.example.smartwardrobe.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PatchMapping("/admin/users/{userId}/roles")
    public ResponseEntity<Void> changRole(@PathVariable Long userId,
                                          @RequestBody RoleChangeRequest request){
        userService.changeUserRole(userId, request);
        return ResponseEntity.ok().build();
    }
}

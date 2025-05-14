package com.example.smartwardrobe.user.service;

import com.example.smartwardrobe.auth.dto.request.RoleChangeRequest;
import com.example.smartwardrobe.user.entity.User;
import com.example.smartwardrobe.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public User changeUserRole(Long userId, RoleChangeRequest req) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "해당 사용자를 찾을 수 없습니다."
                ));
        User updated = User.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .nickname(user.getNickname())
                .role(req.getRole())
                .build();
        return userRepository.save(updated);
    }
}

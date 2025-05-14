package com.example.smartwardrobe.user.service;

import com.example.smartwardrobe.auth.dto.request.RoleChangeRequest;
import com.example.smartwardrobe.user.entity.User;
import com.example.smartwardrobe.user.entity.UserRole;
import com.example.smartwardrobe.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

    @Test
    @DisplayName("관리자 권한 변경 성공: 기존 사용자 ROLE이 ADMIN으로 바뀌어 저장된다")
    void changeUserRole_success() {
        // given
        User existing = User.of("user1", "pw", "nick", UserRole.ROLE_USER);
        when(userRepository.findById(1L)).thenReturn(Optional.of(existing));

        RoleChangeRequest req = new RoleChangeRequest(UserRole.ROLE_ADMIN);
        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);

        // when
        userService.changeUserRole(1L, req);

        // then
        verify(userRepository).save(captor.capture());
        User saved = captor.getValue();
        assertEquals(existing.getId(),       saved.getId());
        assertEquals(existing.getUsername(), saved.getUsername());
        assertEquals(existing.getPassword(), saved.getPassword());
        assertEquals(existing.getNickname(), saved.getNickname());
        assertEquals(UserRole.ROLE_ADMIN,    saved.getRole());
    }

    @Test
    @DisplayName("관리자 권한 변경 실패: 해당 사용자가 없으면 404 NOT_FOUND 예외 발생")
    void changeUserRole_notFound_throws() {
        // given
        when(userRepository.findById(42L)).thenReturn(Optional.empty());
        RoleChangeRequest req = new RoleChangeRequest(UserRole.ROLE_ADMIN);

        // when & then
        ResponseStatusException ex = assertThrows(
                ResponseStatusException.class,
                () -> userService.changeUserRole(42L, req)
        );

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
        assertTrue(ex.getMessage().contains("해당 사용자를 찾을 수 없습니다."));
    }

}

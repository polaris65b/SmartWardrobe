package com.example.smartwardrobe.user.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    @Builder
    private User(Long id, String username, String password, String nickname, UserRole role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.role = role;
    }

    public static User of(String username, String password, String nickname, UserRole role) {
        return User.builder()
                .username(username)
                .password(password)
                .nickname(nickname)
                .role(role)
                .build();
    }
}

package com.example.blocking_service.domain.dto;

import com.example.blocking_service.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String userId;
    private String password;

    public static UserDto of(Long id, String userId, String password) {
        return new UserDto(id, userId, password);
    }

    public static UserDto of(String userId, String password) {
        return new UserDto(null, userId, password);
    }

    public User toEntity() {
        return User.of(userId, password);
    }
}
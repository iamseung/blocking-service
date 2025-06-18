package com.example.blocking_service.dto;

import com.example.blocking_service.entity.User;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SignUpRequestDto {

    @NotBlank private String userId;
    @NotBlank private String password;

    public User toEntity() {
        return User.of(userId, password);
    }

    public UserDto toDto() {
        return UserDto.of(userId, password);
    }
}
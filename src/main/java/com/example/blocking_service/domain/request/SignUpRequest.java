package com.example.blocking_service.domain.request;

import com.example.blocking_service.domain.dto.UserDto;
import com.example.blocking_service.domain.entity.User;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SignUpRequest {
    @NotBlank private String userId;
    @NotBlank private String password;

    public User toEntity() {
        return User.of(userId, password);
    }

    public UserDto toDto() {
        return UserDto.of(userId, password);
    }
}
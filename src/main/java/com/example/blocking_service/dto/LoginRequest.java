package com.example.blocking_service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {

    @NotBlank private String userId;
    @NotBlank private String password;
}

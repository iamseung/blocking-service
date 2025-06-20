package com.example.blocking_service.controller;

import com.example.blocking_service.domain.response.ApiSuccessResponse;
import com.example.blocking_service.domain.request.LoginRequest;
import com.example.blocking_service.domain.request.SignUpRequest;
import com.example.blocking_service.jwt.JwtUtil;
import com.example.blocking_service.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    // 1. 회원 가입 API
    @PostMapping("/signup")
    public ResponseEntity<ApiSuccessResponse<Void>> signup(@Valid @RequestBody SignUpRequest dto) {
        userService.createUser(dto.toDto());
        return ResponseEntity.ok(ApiSuccessResponse.ok());
    }

    // 2. 로그인 API
    @PostMapping("/login")
    public ResponseEntity<ApiSuccessResponse<String>> login(@Valid @RequestBody LoginRequest dto) throws AuthenticationException {
        // 사용자 인증
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getUserId(), dto.getPassword()));

        // 토큰 생성, userId 기반
        String token = jwtUtil.generateToken(dto.getUserId());

        return ResponseEntity.ok(ApiSuccessResponse.ok(token));
    }

    // 2. 로그인 API
    @PostMapping("/login/v2")
    public ResponseEntity<Void> loginV2(@Valid @RequestBody LoginRequest dto) throws AuthenticationException {
        // 사용자 인증
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getUserId(), dto.getPassword()));

        // 토큰 생성, userId 기반
        String token = jwtUtil.generateToken(dto.getUserId());

        ResponseCookie cookie = ResponseCookie.from("access_token", token)
                .httpOnly(false)
                .secure(false)
                .path("/")
                .sameSite("Lax")
                .maxAge(Duration.ofHours(1))
                .build();

        return ResponseEntity
                .ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .build();
    }
}

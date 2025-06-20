package com.example.blocking_service.service;

import com.example.blocking_service.domain.dto.UserDto;
import com.example.blocking_service.domain.entity.User;
import com.example.blocking_service.exception.BaseException;
import com.example.blocking_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.blocking_service.exception.ErrorCode.DUPLICATE_USER_ID;
import static com.example.blocking_service.exception.ErrorCode.NOT_EXIST_USER;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void createUser(UserDto dto) {
        createUserValidation(dto);
        User user = dto.toEntity();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public void createUserValidation(UserDto dto) {
        if(userRepository.findByUserId(dto.getUserId()).isPresent()) {
            throw new BaseException(DUPLICATE_USER_ID, "중복된 아이디 입니다. userId : %s ".formatted(dto.getUserId()));
        }
    }

    @Transactional(readOnly = true)
    public User findUserById(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new BaseException(NOT_EXIST_USER, "존재하지 않는 유저입니다. userId : %s".formatted(id))
        );
    }
}
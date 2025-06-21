package com.example.blocking_service.service;

import com.example.blocking_service.domain.dto.ExtensionDto;
import com.example.blocking_service.domain.entity.Extension;
import com.example.blocking_service.domain.entity.ExtensionType;
import com.example.blocking_service.domain.entity.User;
import com.example.blocking_service.domain.entity.UserExtension;
import com.example.blocking_service.exception.BaseException;
import com.example.blocking_service.repository.UserExtensionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.blocking_service.exception.ErrorCode.NOT_VALIDATE_EXTENSION;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserExtensionService {

    private final UserExtensionRepository userExtensionRepository;

    public void createCustomExtensionValidator(Long userId, String extensionName) {
        if(validateDuplicate(userId, extensionName)) {
            throw new BaseException(NOT_VALIDATE_EXTENSION, "이미 등록된 커스텀 확장자입니다.");
        }

        if(validateCustomExtensionLimit(userId)) {
            throw new BaseException(NOT_VALIDATE_EXTENSION, "커스텀 확장자는 최대 200개까지 등록 가능합니다.");
        }
    }

    private boolean validateDuplicate(Long userId, String extensionName) {
        return userExtensionRepository.existsByUserIdAndExtension_NameAndExtension_ExtensionType(
                userId, extensionName, ExtensionType.CUSTOM
        );
    }

    private boolean validateCustomExtensionLimit(Long userId) {
        long count = userExtensionRepository.countCustomExtensionsByUserId(userId);
        return count >= 200;
    }

    @Transactional
    public void createUserExtension(User user, Extension extension) {
        userExtensionRepository.save(UserExtension.of(user, extension));
    }

    // 커스텀 확장자 조회
    @Transactional(readOnly = true)
    public List<ExtensionDto> getCustomExtensions(Long userId) {
        return userExtensionRepository.findByUserIdAndExtension_ExtensionType(userId, ExtensionType.CUSTOM).stream()
                .map(ue -> new ExtensionDto(ue.getId(), ue.getExtension().getName(), false))
                .toList();
    }

    // 고정 확장자 조회
    @Transactional(readOnly = true)
    public List<UserExtension> getFixedExtensions(Long userId) {
        return userExtensionRepository.findByUserIdAndExtension_ExtensionType(userId, ExtensionType.FIXED);
    }

    @Transactional
    public void deleteByUserIdAnd_ExtensionId(Long userId, Long extensionId) {
        userExtensionRepository.deleteByUserIdAnd_ExtensionId(userId, extensionId);
    }

    @Transactional
    public void deleteByUserIdAndExtensionId(Long userId, Long extensionId) {
        userExtensionRepository.deleteByUserIdAndExtensionId(userId, extensionId);
    }

    // UserExtension 조회, userId & extensionId
    public UserExtension getUserExtensionByUserIdAndExtensionId(Long userId, Long extensionId) {
        return userExtensionRepository.findByUserIdAndExtensionId(userId, extensionId).orElse(null);
    }
}

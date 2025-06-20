package com.example.blocking_service.service;

import com.example.blocking_service.domain.dto.UserDto;
import com.example.blocking_service.domain.entity.Extension;
import com.example.blocking_service.domain.entity.ExtensionType;
import com.example.blocking_service.domain.entity.User;
import com.example.blocking_service.exception.BaseException;
import com.example.blocking_service.repository.ExtensionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.blocking_service.exception.ErrorCode.NOT_EXIST_EXTENSION;

@Service
@RequiredArgsConstructor
public class ExtensionService {

    private final ExtensionRepository extensionRepository;

    // 고정 확장자 전체 조회
    @Transactional(readOnly = true)
    public List<Extension> getFixedExtensions() {
        return extensionRepository.findByExtensionType(ExtensionType.FIXED);
    }

    // 고정 확장자 단일 조회
    @Transactional(readOnly = true)
    public Extension getFixedExtension(Long extensionId) {
        return extensionRepository.findById(extensionId)
                .orElseThrow(() -> new BaseException(NOT_EXIST_EXTENSION, "존재하지 않는 확장자 ID 입니다. extensionId : %s".formatted(extensionId)));
    }

    // 단일 커스텀 확장자 조회, 없으면 생성
    @Transactional(readOnly = true)
    public Extension getCustomExtension(String extensionName) {
        return extensionRepository
                .findByNameAndExtensionType(extensionName, ExtensionType.CUSTOM)
                .stream().findFirst()
                .orElseGet(() -> extensionRepository.save(Extension.of(extensionName, ExtensionType.CUSTOM)));
    }


}

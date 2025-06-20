package com.example.blocking_service.service;

import com.example.blocking_service.domain.dto.ExtensionDto;
import com.example.blocking_service.domain.dto.UserDto;
import com.example.blocking_service.domain.entity.Extension;
import com.example.blocking_service.domain.entity.User;
import com.example.blocking_service.domain.entity.UserExtension;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BlockingService {

    private final ExtensionService extensionService;
    private final UserExtensionService userExtensionService;
    private final UserService userService;

    // 커스텀 확장자 조회
    @Transactional
    public void createCustomExtension(String extensionName, UserDto userDto) {
        Extension extension = extensionService.getCustomExtension(extensionName);
        User user = userService.findUserById(userDto.getId());

        // 예외 처리
        userExtensionService.createCustomExtensionValidator(user.getId(), extensionName);
        // 연관 관계 매핑
        userExtensionService.createUserExtension(user, extension);
    }

    // 커스텀 확장자 조회
    @Transactional(readOnly = true)
    public List<ExtensionDto> getCustomExtensions(Long userId) {
        return userExtensionService.getCustomExtensions(userId);
    }

    // 커스텀 확장자 삭제
    @Transactional
    public void deleteCustomExtension(Long userId, Long extensionId) {
        userExtensionService.deleteByUserIdAndExtensionId(userId,extensionId);
    }

    // 고정 확장자 토글
    @Transactional
    public void toggleFixedExtension(UserDto userDto, Long extensionId) {
        // 조회
        Extension extension = extensionService.getFixedExtension(extensionId);
        // 연관 관계 확인
        UserExtension userExtension = userExtensionService.getUserExtensionByUserIdAndExtensionId(userDto.getId(), extensionId);

        if(userExtension == null) {
            User user = userService.findUserById(userDto.getId());
            userExtensionService.createUserExtension(user, extension);
        } else {
            userExtensionService.deleteByUserIdAndExtensionId(userDto.getId(), extensionId);
        }
    }

    // 고정 확장자 조회
    @Transactional(readOnly = true)
    public List<ExtensionDto> getFixedExtensionsByUser(UserDto userDto) {
        // DB 에서 관리하는 고정 확장자
        List<Extension> fixedExtensions = extensionService.getFixedExtensions();
        // 연관 관계 조회
        List<UserExtension> userExtensions = userExtensionService.getFixedExtensions(userDto.getId());

        return parseFixedDto(fixedExtensions, userExtensions);
    }

    private List<ExtensionDto> parseFixedDto(List<Extension> fixedExtensions, List<UserExtension> userExtensions) {
        // 1. 유저가 체크한 확장자의 ID를 Set으로 정리
        Set<Long> checkedExtensionIds = userExtensions.stream()
                .map(ue -> ue.getExtension().getId())
                .collect(Collectors.toSet());

        // 2. 전체 확장자와 체크 여부 매핑
        return fixedExtensions.stream()
                .map(ext -> new ExtensionDto(ext.getId(), ext.getName(), checkedExtensionIds.contains(ext.getId())))
                .toList();
    }
}

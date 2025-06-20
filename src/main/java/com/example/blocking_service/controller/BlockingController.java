package com.example.blocking_service.controller;

import com.example.blocking_service.domain.response.ApiSuccessResponse;
import com.example.blocking_service.domain.request.CreateCustomExtensionRequest;
import com.example.blocking_service.domain.dto.CustomUserDetails;
import com.example.blocking_service.domain.dto.ExtensionDto;
import com.example.blocking_service.service.BlockingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/blocking/extensions")
@RestController
@RequiredArgsConstructor
public class BlockingController {

    private final BlockingService blockingService;

    // 1. 커스텀 확장자 생성
    @PostMapping("/custom")
    public ResponseEntity<ApiSuccessResponse<Void>> createCustomExtension(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                                          @RequestBody CreateCustomExtensionRequest request) {
        blockingService.createCustomExtension(request.getExtensionName(), userDetails.toDto());
        return ResponseEntity.ok(ApiSuccessResponse.ok());
    }

    // 2. 커스텀 확장자 조회
    @GetMapping("/custom")
    public ResponseEntity<ApiSuccessResponse<List<ExtensionDto>>> getCustomExtensions(@AuthenticationPrincipal CustomUserDetails userDetails) {
        List<ExtensionDto> customExtensions = blockingService.getCustomExtensions(userDetails.toDto().getId());
        return ResponseEntity.ok(ApiSuccessResponse.ok(customExtensions));
    }

    // 3. 커스텀 확장자 삭제
    @DeleteMapping("/custom/{extensionId}")
    public ResponseEntity<ApiSuccessResponse<Void>> deleteCustomExtension(@AuthenticationPrincipal CustomUserDetails userDetails,
                                      @PathVariable Long extensionId) {
        blockingService.deleteCustomExtension(userDetails.toDto().getId(), extensionId);
        return ResponseEntity.ok(ApiSuccessResponse.ok());
    }

    // 4. 고정 확장자 Toggle
    @PatchMapping("/fixed/{extensionId}/toggle")
    public ResponseEntity<ApiSuccessResponse<Void>> toggleFixedExtension(@AuthenticationPrincipal CustomUserDetails userDetails,
                                     @PathVariable Long extensionId) {
        blockingService.toggleFixedExtension(userDetails.toDto(), extensionId);
        return ResponseEntity.ok(ApiSuccessResponse.ok());
    }

    // 5. 고정 확장자 조회
    @GetMapping("/fixed")
    public ResponseEntity<ApiSuccessResponse<List<ExtensionDto>>> getFixedExtensions(@AuthenticationPrincipal CustomUserDetails userDetails) {
        List<ExtensionDto> fixedExtensionsByUser = blockingService.getFixedExtensionsByUser(userDetails.toDto());
        return ResponseEntity.ok(ApiSuccessResponse.ok(fixedExtensionsByUser));
    }
}

package com.example.blocking_service.component;

import com.example.blocking_service.service.ExtensionService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ExtensionCleaner {

    private final ExtensionService extensionService;

    // 사용하지 않는 커스텀 확장자 정기 삭제, 실제 동작은 Block
    // @Scheduled(cron = "0 0 1 * * *")
    public void cleanUpUnusedCustomExtensions() {
        extensionService.cleanUpOrphanedCustomExtensions();
    }
}

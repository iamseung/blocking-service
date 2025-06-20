package com.example.blocking_service.domain.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateCustomExtensionRequest {
    private String extensionName;
}

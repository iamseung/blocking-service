package com.example.blocking_service.service;

import com.example.blocking_service.repository.UserExtensionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserExtensionService {

    private final UserExtensionRepository userExtensionRepository;
}

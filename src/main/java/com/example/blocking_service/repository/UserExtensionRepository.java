package com.example.blocking_service.repository;

import com.example.blocking_service.entity.UserExtension;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserExtensionRepository extends JpaRepository<UserExtension, Long> {
}

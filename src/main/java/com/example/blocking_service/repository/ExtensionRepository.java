package com.example.blocking_service.repository;

import com.example.blocking_service.entity.Extension;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExtensionRepository extends JpaRepository<Extension, Long> {
}

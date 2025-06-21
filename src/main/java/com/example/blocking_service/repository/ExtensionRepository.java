package com.example.blocking_service.repository;

import com.example.blocking_service.domain.entity.Extension;
import com.example.blocking_service.domain.entity.ExtensionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExtensionRepository extends JpaRepository<Extension, Long> {
    Optional<Extension> findByNameAndExtensionType(String extensionName, ExtensionType extensionType);

    // 고정 확장자 전체 목록 조회용
    List<Extension> findByExtensionType(ExtensionType extensionType);

    @Query("""
    SELECT e FROM Extension e
    WHERE e.extensionType = 'CUSTOM'
    AND NOT EXISTS (
        SELECT ue FROM UserExtension ue WHERE ue.extension = e
    )
""")
    List<Extension> findAllCustomExtensionsWithNoUser();
}

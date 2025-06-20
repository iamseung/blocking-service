package com.example.blocking_service.repository;

import com.example.blocking_service.domain.entity.ExtensionType;
import com.example.blocking_service.domain.entity.UserExtension;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserExtensionRepository extends JpaRepository<UserExtension, Long> {
    @EntityGraph(attributePaths = {"extension"})
    List<UserExtension> findByUserIdAndExtension_ExtensionType(Long userId, ExtensionType extensionType);

    Optional<UserExtension> findByUserIdAndExtensionId(Long userId, Long extensionId);

    // 특정 확장자 설정이 존재하는지 확인 (중복 등록 방지용)
    boolean existsByUserIdAndExtension_NameAndExtension_ExtensionType(Long userId, String extensionName, ExtensionType extensionType);

    // 유저가 등록한 커스텀 확장자 개수 카운트
    @Query("SELECT COUNT(ue) FROM UserExtension ue WHERE ue.user.id = :userId AND ue.extension.extensionType = 'CUSTOM'")
    long countCustomExtensionsByUserId(@Param("userId") Long userId);

    @Modifying
    @Query("DELETE FROM UserExtension ue WHERE ue.user.id = :userId AND ue.id = :extensionId")
    void deleteByUserIdAndExtensionId(@Param("userId") Long userId, @Param("extensionId") Long extensionId);

}

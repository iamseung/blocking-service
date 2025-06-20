package com.example.blocking_service.domain.entity;

import com.example.blocking_service.exception.BaseException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.example.blocking_service.exception.ErrorCode.NOT_VALIDATE_EXTENSION;

@Getter
@Entity
@Table(name = "extension")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Extension extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 20)
    private String name; // 확장자 명

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ExtensionType extensionType; // 확장자 유형 (고정/커스텀)

    private Extension(Long id, String name, ExtensionType extensionType) {
        validate(name, extensionType);
        this.id = id;
        this.name = name;
        this.extensionType = extensionType;
    }

    private Extension(String name, ExtensionType extensionType) {
        validate(name, extensionType);
        this.name = name;
        this.extensionType = extensionType;
    }

    public static Extension of(Long id, String name, ExtensionType extensionType) {
           return new Extension(id, name, extensionType);
    }

    public static Extension of(String name, ExtensionType extensionType) {
        return new Extension(name, extensionType);
    }

    private void validate(String name, ExtensionType type) {
        if (name == null || name.trim().isEmpty()) {
            throw new BaseException(NOT_VALIDATE_EXTENSION, "확장자명이 비어있을 수 없습니다.");
        }

        if (name.length() > 20) {
            throw new BaseException(NOT_VALIDATE_EXTENSION, "확장자명은 20자 이내여야 합니다.");
        }

        if (type == null) {
            throw new BaseException(NOT_VALIDATE_EXTENSION, "ExtensionType은 필수입니다.");
        }
    }
}

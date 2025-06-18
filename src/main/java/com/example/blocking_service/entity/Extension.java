package com.example.blocking_service.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "extension")
public class Extension extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 20)
    private String name; // 확장자 명

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ExtensionType extensionType; // 확장자 유형 (고정/커스텀)
}

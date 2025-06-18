package com.example.blocking_service.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "user_extension", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "extension_id"})
})
public class UserExtension {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "extension_id")
    private Extension extension;

    @Column(nullable = false)
    private boolean isChecked; // 체크 여부 (고정 확장자에만)
}
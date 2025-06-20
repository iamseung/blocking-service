package com.example.blocking_service.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "user_extension", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "extension_id"})
})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    private UserExtension(Long id, User user, Extension extension) {
        this.id = id;
        this.user = user;
        this.extension = extension;
    }

    private UserExtension(User user, Extension extension) {
        this.user = user;
        this.extension = extension;
    }

    public static UserExtension of(Long id, User user, Extension extension) {
        return new UserExtension(id, user, extension);
    }

    public static UserExtension of(User user, Extension extension) {
        return new UserExtension(user, extension);
    }
}
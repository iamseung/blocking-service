
package com.example.blocking_service.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@Table(name = "user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String userId;

    @Column(nullable = false)
    @Setter private String password;

    private User(Long id, String userId, String password) {
        this.id = id;
        this.userId = userId;
        this.password = password;
    }

    private User(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    public static User of(Long id, String userId, String password) {
        return new User(id, userId, password);
    }

    public static User of(String userId, String password) {
        return new User(userId, password);
    }
}

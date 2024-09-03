package com.example.ondosocial.domain.user.entity;

import com.example.ondosocial.config.entity.BaseEntity;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class User extends BaseEntity {
    private String email;
    private String password;
    private String name;
    private boolean deleted;

    public User(String email, String password, String name, boolean deleted) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.deleted = deleted;
    }
}

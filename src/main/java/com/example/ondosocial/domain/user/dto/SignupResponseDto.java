package com.example.ondosocial.domain.user.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class SignupResponseDto {

    private final Long id;
    private final String email;
    private final String password;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public SignupResponseDto(Long id, String email, String name,
                             LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.email = email;
        this.password = name;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}

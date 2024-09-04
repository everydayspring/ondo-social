package com.example.ondosocial.domain.post.dto;

import com.example.ondosocial.domain.user.entity.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostResponseDto {
    private final String title;
    private final String contents;
    private final int celsius;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public PostResponseDto(String title, String contents, int celsius, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.title = title;
        this.contents = contents;
        this.celsius = celsius;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
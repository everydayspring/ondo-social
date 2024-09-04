package com.example.ondosocial.domain.post.dto;

import java.time.LocalDateTime;

public class PostSimpleResponseDto {
    private final String title;
    private final String  contents;
    private final int celsius;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public PostSimpleResponseDto(String title, String contents, int celsius, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.title = title;
        this.contents = contents;
        this.celsius = celsius;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}

package com.example.ondosocial.domain.post.dto;

public class PostUpdateResponseDto {
    private final Long id;
    private final String title;
    private final String contents;

    public PostUpdateResponseDto(Long id, String title, String contents) {
        this.id = id;
        this.title = title;
        this.contents = contents;
    }
}

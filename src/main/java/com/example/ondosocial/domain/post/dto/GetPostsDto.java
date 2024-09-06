package com.example.ondosocial.domain.post.dto;

import java.time.LocalDateTime;

import com.example.ondosocial.domain.post.entity.Post;

import lombok.Getter;

public class GetPostsDto {
    @Getter
    public static class Response {
        private final String name;
        private final String title;
        private final int celsius;
        private final LocalDateTime createdAt;

        public Response(Post post) {
            this.name = post.getUser().getName();
            this.title = post.getTitle();
            this.celsius = post.getCelsius();
            this.createdAt = post.getCreatedAt();
        }
    }
}

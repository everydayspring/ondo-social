package com.example.ondosocial.domain.post.dto;

import com.example.ondosocial.domain.post.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;

public class GetPostDto {
    @Getter
    public static class Response {
        private final String email;
        private final String name;
        private final String title;
        private final String content;
        private final int celsius;
        private final LocalDateTime createdAt;
        private final LocalDateTime updatedAt;

        public Response(Post post) {
            this.email = post.getUser().getEmail();
            this.name = post.getUser().getName();
            this.title = post.getTitle();
            this.content = post.getContent();
            this.celsius = post.getCelsius();
            this.createdAt = post.getCreatedAt();
            this.updatedAt = post.getUpdatedAt();
        }
    }
}

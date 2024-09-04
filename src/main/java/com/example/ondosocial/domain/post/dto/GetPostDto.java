package com.example.ondosocial.domain.post.dto;

import com.example.ondosocial.domain.post.entity.Post;
import com.example.ondosocial.domain.user.entity.User;
import lombok.Getter;

import java.time.LocalDateTime;

public class GetPostDto {
    @Getter
    public static class Response {
        private String email;
        private String name;
        private String title;
        private String content;
        private int celsius;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

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

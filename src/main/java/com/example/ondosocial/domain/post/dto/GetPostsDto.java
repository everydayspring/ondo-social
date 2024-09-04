package com.example.ondosocial.domain.post.dto;

import com.example.ondosocial.domain.post.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;

public class GetPostsDto {
    @Getter
    public static class Response {
        private String name;
        private String title;
        private int celsius;
        private LocalDateTime createdAt;

        public Response(Post post) {
            this.name = post.getUser().getName();
            this.title = post.getTitle();
            this.celsius = post.getCelsius();
            this.createdAt = post.getCreatedAt();
        }
    }
}

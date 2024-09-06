package com.example.ondosocial.domain.post.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import com.example.ondosocial.domain.post.entity.Post;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class PostUpdateDto {
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {
        @NotBlank private String title;

        @NotBlank private String content;

        @NotNull
        @Min(1)
        @Max(100)
        private int celsius;
    }

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

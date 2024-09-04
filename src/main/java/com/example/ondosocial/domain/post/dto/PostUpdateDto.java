package com.example.ondosocial.domain.post.dto;

import com.example.ondosocial.domain.post.entity.Post;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class PostUpdateDto {
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {
        @NotBlank
        private String title;
        @NotBlank
        private String content;
        @Max(100)
        @Min(1)
        private int celsius;
    }

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

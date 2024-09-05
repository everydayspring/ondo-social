package com.example.ondosocial.domain.post.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class PostCreateDto {
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {
        @NotBlank
        private String title;

        @NotBlank
        private String content;

        @NotNull
        @Min(1)
        @Max(100)
        private int celsius;
    }
}

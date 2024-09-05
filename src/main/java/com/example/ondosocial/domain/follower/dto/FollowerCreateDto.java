package com.example.ondosocial.domain.follower.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class FollowerCreateDto {
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {
        @NotNull
        private Long followerId;
    }
}

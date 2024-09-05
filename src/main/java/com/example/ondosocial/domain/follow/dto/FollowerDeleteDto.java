package com.example.ondosocial.domain.follow.dto;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class FollowerDeleteDto {
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {
        @NotNull private Long followerId;
    }
}

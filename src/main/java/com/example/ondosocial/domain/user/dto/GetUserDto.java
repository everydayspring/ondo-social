package com.example.ondosocial.domain.user.dto;

import java.time.LocalDateTime;

import com.example.ondosocial.domain.user.entity.User;

import lombok.Getter;

public class GetUserDto {
    @Getter
    public static class DetailResponse {
        private final Long id;
        private final String name;
        private final String email;
        private final double averageCelsius;
        private final Integer postCount;
        private final Integer followerCount;
        private final LocalDateTime createdAt;
        private final LocalDateTime updatedAt;

        public DetailResponse(double averageCelsius, User user) {
            this.id = user.getId();
            this.name = user.getName();
            this.email = user.getEmail();
            this.averageCelsius = averageCelsius;
            this.postCount = user.getPosts().size();
            this.followerCount = user.getFollows().size();
            this.createdAt = user.getCreatedAt();
            this.updatedAt = user.getUpdatedAt();
        }
    }

    @Getter
    public static class SimpleResponse {
        private final Long id;
        private final String name;
        private final String email;
        private final double averageCelsius;
        private final Integer postCount;
        private final Integer followerCount;

        public SimpleResponse(double averageCelsius, User user) {
            this.id = user.getId();
            this.name = user.getName();
            this.email = user.getEmail();
            this.averageCelsius = averageCelsius;
            this.postCount = user.getPosts().size();
            this.followerCount = user.getFollows().size();
        }
    }

    @Getter
    public static class ServiseResponse {
        private final double averageCelsius;
        private final User user;

        public ServiseResponse(double averageCelsius, User user) {
            this.averageCelsius = averageCelsius;
            this.user = user;
        }
    }
}

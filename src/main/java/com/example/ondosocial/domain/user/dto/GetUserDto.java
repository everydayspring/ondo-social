package com.example.ondosocial.domain.user.dto;

import com.example.ondosocial.domain.user.entity.User;
import lombok.Getter;

import java.time.LocalDateTime;

public class GetUserDto {
    @Getter
    public static class DetailResponse {
        private final Long id;
        private final String name;
        private final String email;
        private final Integer postCount;
        private final Integer followerCount;
        private final LocalDateTime createdAt;
        private final LocalDateTime updatedAt;

        public DetailResponse(User user) {
            this.id = user.getId();
            this.name = user.getName();
            this.email = user.getEmail();
            this.postCount = user.getPosts().size();
            this.followerCount = user.getFollowers().size();
            this.createdAt = user.getCreatedAt();
            this.updatedAt = user.getUpdatedAt();
        }
    }

    @Getter
    public static class SimpleResponse {
        private final Long id;
        private final String name;
        private final String email;
        private final Integer postCount;
        private final Integer followerCount;

        public SimpleResponse(User user) {
            this.id = user.getId();
            this.name = user.getName();
            this.email = user.getEmail();
            this.postCount = user.getPosts().size();
            this.followerCount = user.getFollowers().size();
        }
    }
}

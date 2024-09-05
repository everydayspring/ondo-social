package com.example.ondosocial.domain.user.dto;

import com.example.ondosocial.domain.user.entity.User;
import lombok.Getter;

import java.time.LocalDateTime;

public class GetUserDto {
    @Getter
    public static class DetailResponse {
        private Long id;
        private String name;
        private String email;
        private Integer postCount;
        private Integer followerCount;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

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
        private Long id;
        private String name;
        private String email;
        private Integer postCount;
        private Integer followerCount;

        public SimpleResponse(User user) {
            this.id = user.getId();
            this.name = user.getName();
            this.email = user.getEmail();
            this.postCount = user.getPosts().size();
            this.followerCount = user.getFollowers().size();
        }
    }
}

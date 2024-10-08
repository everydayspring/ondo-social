package com.example.ondosocial.domain.follow.dto;

import com.example.ondosocial.domain.user.entity.User;

import lombok.Getter;

public class GetFollowersDto {

    @Getter
    public static class Response {
        private final Long id;
        private final String name;

        public Response(User user) {
            this.id = user.getId();
            this.name = user.getName();
        }
    }
}

package com.example.ondosocial.domain.post.dto;

import com.example.ondosocial.domain.user.entity.User;
import lombok.Getter;

@Getter
public class PostRequestDto {
    private String title;
    private String contents;
    private int celsius;
    private User user;
}

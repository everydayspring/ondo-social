package com.example.ondosocial.domain.profile.dto.response;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProfileResponseDto {
    private Long id;
    private String name;
    private String email;
//    private Integer postCount;
//    private Integer followerCount;
}

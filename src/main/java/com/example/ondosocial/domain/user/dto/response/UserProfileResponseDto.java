package com.example.ondosocial.domain.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserProfileResponseDto {
    private String message;
    private Integer statusCode;
    private UserProfileDto userProfileDto;
}

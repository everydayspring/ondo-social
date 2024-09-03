package com.example.ondosocial.domain.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class UserUpdateResponseDto {
    private String message;
    private Integer statusCode;
    private UserUpdateDto userUpdateDto;
}

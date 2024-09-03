package com.example.ondosocial.domain.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserUpdateResponseDto {
    private String message;
    private Integer statusCode;
    private Long id;
}

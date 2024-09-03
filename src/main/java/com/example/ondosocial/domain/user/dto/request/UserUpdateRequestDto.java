package com.example.ondosocial.domain.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class UserUpdateRequestDto {
    private String name;
    private String email;
    private String password;
}

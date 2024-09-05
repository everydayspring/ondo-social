package com.example.ondosocial.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserUpdateDto {
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    static public class Request {
        @NotBlank
        private String name;

        @Email
        @NotBlank
        private String email;

        @NotBlank
        private String password;

        private String newPassword;
    }
}

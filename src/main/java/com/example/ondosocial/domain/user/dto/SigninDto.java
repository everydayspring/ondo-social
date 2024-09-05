package com.example.ondosocial.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class SigninDto {
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {
        @Email @NotBlank private String email;

        @NotBlank private String password;
    }
}

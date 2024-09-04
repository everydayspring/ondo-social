package com.example.ondosocial.domain.profile.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class UserUpdateRequestDto {
    @NotBlank
    private String name;
    @Email
    @NotBlank
    private String email;
    @NotBlank
    private String currentPassword;
    @NotBlank
    private String newPassword;
}

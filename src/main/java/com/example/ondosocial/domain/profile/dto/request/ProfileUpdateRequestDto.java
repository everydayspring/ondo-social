package com.example.ondosocial.domain.profile.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ProfileUpdateRequestDto {
    @NotBlank
    private String name;
    @Email
    private String email;
    @NotBlank
    private String currentPassword;
    private String newPassword;
}

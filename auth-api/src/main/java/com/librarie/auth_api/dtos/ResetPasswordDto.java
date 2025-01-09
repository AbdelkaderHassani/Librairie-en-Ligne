package com.librarie.auth_api.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class ResetPasswordDto {
    @NotEmpty(message = "le mot de passe est obligatoire")
    private String newPassword;

}

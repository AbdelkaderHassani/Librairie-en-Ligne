package com.librarie.auth_api.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ForgotPasswordDto {
    @Email(message = "veuillez entrer un email valide")
    @NotBlank(message = "l'email est obligatoire")
    private String email;

}

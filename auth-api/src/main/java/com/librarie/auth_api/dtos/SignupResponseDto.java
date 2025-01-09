package com.librarie.auth_api.dtos;

import com.librarie.auth_api.entities.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupResponseDto {
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private Gender gender;

}

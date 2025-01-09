package com.librarie.auth_api.dtos;

import com.librarie.auth_api.entities.Gender;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.NonNull;

@Getter
@Setter
public class RegisterUserDto {

    @NotEmpty(message = "le nom est obligatoire")
    private String nom;

    @NotEmpty(message = "le prenom est obligatoire")
    @NotNull(message = "Le prénom ne peut pas être nul")
    private String prenom;


    @Email(message = "veuillez entrer un email valide")
    private String email;

    @Pattern(
            regexp = "^(?=(.*\\d.*){3})(?=(.*[a-z].*){2})(?=(.*[A-Z].*){2})(?=(.*[\\W_].*){1}).{8,}$",
            message = "Le mot de passe doit contenir au moins 8 caractères, 3 chiffres, 2 majuscules, 2 minuscules et 1 caractère spécial."
    )
    private String motDePasse;
@Column(nullable = false)
@NotNull(message = "Le genre est obligatoire.")
    private Gender gender;


    @NotEmpty(message = "veuillez confirmer le mot de passe ")
    private String retypePassword;
    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }
    public  String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }
}

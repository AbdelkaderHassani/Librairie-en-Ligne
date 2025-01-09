package com.librarie.auth_api.dtos;

import com.librarie.auth_api.entities.EnumRole;
import com.librarie.auth_api.entities.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserDto {
    private String nom;
    private String prenom;
    private String email;
    private Gender gender;
    private Set<EnumRole> roles; // Nouveau champ pour les rôles

    // Getters et Setters
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<EnumRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<EnumRole> roles) {
        this.roles = roles;
    }
}

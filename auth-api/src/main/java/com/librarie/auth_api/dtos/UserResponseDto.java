package com.librarie.auth_api.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponseDto {
    private Integer id;
    private String nom;
    private String prenom;
    private String email;
    private String message; // Si vous voulez un message de type "Access denied" ou autre.

    // Vous pouvez également ajouter un constructeur sans message si nécessaire.
    public UserResponseDto(Integer id, String nom, String prenom, String email) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
    }

    // Constructeur pour les messages d'erreur ou succès
    public UserResponseDto(String message) {
        this.message = message;
    }
}

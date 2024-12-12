package com.librarie.commande_service.dto;


import lombok.Data;



@Data
public class LivreDto {


        private Long id;       // ID du livre
        private String titre;  // Titre du livre
        private String auteur; // Auteur du livre
        private double prix;   // Prix du livre
    }


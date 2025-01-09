package com.librarie.livre_service.model.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LivreUpdateDTO {

    private Long id;
    private String titre;
    private String auteur;
    private double prix;
    private Long categorieId;  // ID de la catégorie
}

package com.librarie.commande_service.model.entities.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LivreDto {

    private Long id;
    private String titre;
    private double prix;
}

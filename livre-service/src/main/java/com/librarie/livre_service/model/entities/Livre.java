package com.librarie.livre_service.model.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "livre")
public class Livre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titre;
    private String auteur;
    private Double prix;

    @ManyToOne
    @JoinColumn(name = "categorie_id", nullable = true) // Permet des catégories nulles
    @JsonBackReference  // Evite la sérialisation infinie en excluant la catégorie du côté du livre
    private Categorie categorie;
}

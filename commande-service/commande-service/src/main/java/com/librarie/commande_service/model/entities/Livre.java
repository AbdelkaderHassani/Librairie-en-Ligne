package com.librarie.commande_service.model.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Livre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titre;
    private String auteur;
    private double prix;

    @OneToOne(mappedBy = "livre", cascade = CascadeType.ALL, orphanRemoval = true)
    private Commande commande; // Relation 1:1 bidirectionnelle
}
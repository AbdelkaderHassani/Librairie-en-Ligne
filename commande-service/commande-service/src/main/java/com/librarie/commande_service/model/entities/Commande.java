package com.librarie.commande_service.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.engine.profile.Fetch;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "commande")
public class Commande {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long numero;  // Identifiant unique de la commande

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "livre_id", referencedColumnName = "id", nullable = false)  // Clé étrangère vers la table livre
    private Livre livre;

    private int quantity;         // Quantité de livres
    private double totalPrice;    // Prix total de la commande
    private Date date;            // Date de la commande
    private String etat;          // Etat de la commande

    public void setLivreId(Long livreId) {
    }

    // Getters et setters
}
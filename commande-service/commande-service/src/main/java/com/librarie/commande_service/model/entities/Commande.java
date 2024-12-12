package com.librarie.commande_service.model.entities;

import jakarta.persistence.*;
import lombok.*;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Commande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer numero;

    private Date date;
    private String etat;
    private int quantity;
    private double totalPrice;

    @OneToOne
    @JoinColumn(name = "livre_id", referencedColumnName = "id", unique = true, nullable = false)
    private Livre livre;
}
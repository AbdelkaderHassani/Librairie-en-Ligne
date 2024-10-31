package com.librarie.livre_service.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data

public class Livre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titre;
    private String auteur;
    // Constructeurs , Getteurs et Setteurs
    public Livre(Long id , String titre , String auteur){
        this.id=id;
        this.titre=titre;
        this.auteur=auteur;
    }
    public Long getId(){return id;}
    public void setId(Long id) {this.id=id;}
    public String getTitre(){return titre;}
    public void setTitre(String titre) {this.titre=titre;}
    public String getAuteur(){return auteur;}
    public void setAuteur(String auteur) {this.auteur=auteur;}
    }




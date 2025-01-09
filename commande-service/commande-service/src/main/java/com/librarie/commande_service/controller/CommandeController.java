package com.librarie.commande_service.controller;

import com.librarie.commande_service.CommandeServiceApplication;
import com.librarie.commande_service.model.entities.Commande;
import com.librarie.commande_service.repository.CommandeRepository;
import com.librarie.commande_service.service.CommandeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/commandes")  // Spécifie la base pour toutes les routes dans ce contrôleur
public class CommandeController {

    @Autowired
    private CommandeService commandeService;
    @Autowired
    private CommandeRepository commandeRepository;

    @GetMapping  // Ce mapping est maintenant relatif à /api/commandes
    public List<Commande> getAllCommandes() {
        return commandeRepository.findAll(); // Cette ligne pose problème si commandeRepository est null
    }

    @PostMapping
    public ResponseEntity<Commande> createCommande(@RequestParam Long livreId, @RequestParam int quantity) {
        Commande commande = commandeService.createCommande(livreId, quantity);
        return new ResponseEntity<>(commande, HttpStatus.CREATED);
    }
}

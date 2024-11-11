package com.librarie.commande_service.controller;

import com.librarie.commande_service.model.Commande;
import com.librarie.commande_service.repository.CommandeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/commandes")
public class CommandeController {

    @Autowired
    private CommandeRepository commandeRepository;

    // READ - Afficher toutes les commandes
    @GetMapping
    public List<Commande> getAllCommandes() {
        return commandeRepository.findAll();
    }

    // CREATE - Ajouter une nouvelle commande
    @PostMapping
    public ResponseEntity<Commande> createCommande(@RequestBody Commande commande) {
        Commande savedCommande = commandeRepository.save(commande);
        return new ResponseEntity<>(savedCommande, HttpStatus.CREATED);
    }

    // READ - Afficher une commande par ID
    @GetMapping("/{id}")
    public ResponseEntity<Commande> getCommandeById(@PathVariable Long id) {
        Optional<Commande> commande = commandeRepository.findById(id);
        return commande.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // UPDATE - Modifier une commande existante
    @PutMapping("/{id}")
    public ResponseEntity<Commande> updateCommande(@PathVariable Long id, @RequestBody Commande commandeDetails) {
        Optional<Commande> optionalCommande = commandeRepository.findById(id);
        if (optionalCommande.isPresent()) {
            Commande commande = optionalCommande.get();
            commande.setDate(commandeDetails.getDate());
            commande.setEtat(commandeDetails.getEtat());

            Commande updatedCommande = commandeRepository.save(commande);
            return new ResponseEntity<>(updatedCommande, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // DELETE - Supprimer une commande
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteCommande(@PathVariable Long id) {
        try {
            commandeRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

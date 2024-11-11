package com.librarie.livre_service.controller;

import com.librarie.livre_service.model.Livre;
import com.librarie.livre_service.repository.LivreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/livres")
public class LivreController {

    @Autowired
    private LivreRepository livreRepository;

    // READ - Afficher tous les livres
    @GetMapping
    public List<Livre> getAllLivres() {
        return livreRepository.findAll();
    }

    // CREATE - Ajouter un nouveau livre
    @PostMapping
    public ResponseEntity<Livre> createLivre(@RequestBody Livre livre) {
        Livre savedLivre = livreRepository.save(livre);
        return new ResponseEntity<>(savedLivre, HttpStatus.CREATED);
    }

    // READ - Afficher un livre par ID
    @GetMapping("/{id}")
    public ResponseEntity<Livre> getLivreById(@PathVariable Long id) {
        Optional<Livre> livre = livreRepository.findById(id);
        return livre.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    public void setLivreRepository(LivreRepository livreRepository) {
        this.livreRepository = livreRepository;
    }

    public LivreRepository getLivreRepository() {
        return livreRepository;
    }

    // UPDATE - Modifier un livre existant
    /*@PutMapping("/{id}")
    public ResponseEntity<Livre> updateLivre(@PathVariable Long id, @RequestBody Livre livreDetails) {
        if (livreDetails == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Optional<Livre> optionalLivre = livreRepository.findById(id);
        if (optionalLivre.isPresent()) {
            Livre livre = optionalLivre.get();
            livre.setTitre(livreDetails.getTitre());
            livre.setAuteur(livreDetails.getAuteur());
            livre.setPrix(livreDetails.getPrix());

            Livre updatedLivre = livreRepository.save(livre);
            return new ResponseEntity<>(updatedLivre, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }*/

    // DELETE - Supprimer un livre
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteLivre(@PathVariable Long id) {
        try {
            livreRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

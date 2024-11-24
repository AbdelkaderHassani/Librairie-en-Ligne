package com.librarie.livre_service.controller;

import com.librarie.livre_service.model.entities.Livre;
import com.librarie.livre_service.model.entities.Categorie;
import com.librarie.livre_service.repository.LivreRepository;
import com.librarie.livre_service.repository.CategorieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/livres")
public class LivreController {

    @Autowired
    private LivreRepository livreRepository;

    @Autowired
    private CategorieRepository categorieRepository;

    // READ - Afficher tous les livres
    @GetMapping
    public List<Livre> getAllLivres() {
        return livreRepository.findAll();
    }

    // CREATE - Ajouter un nouveau livre
    @PostMapping
    public ResponseEntity<Livre> createLivre(@RequestBody Livre livre) {
        Optional<Categorie> categorieOpt = categorieRepository.findById(livre.getCategorie().getId());

        if (!categorieOpt.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);  // Catégorie invalide
        }

        livre.setCategorie(categorieOpt.get());
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

    // READ - Afficher les livres par catégorie
    @GetMapping("/categorie/{categorieId}")
    public ResponseEntity<List<Livre>> getLivresByCategorie(@PathVariable Long categorieId) {
        Optional<Categorie> categorieOpt = categorieRepository.findById(categorieId);

        if (!categorieOpt.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // Catégorie non trouvée
        }

        List<Livre> livres = livreRepository.findByCategorie(categorieOpt.get());
        return new ResponseEntity<>(livres, HttpStatus.OK);
    }

    // UPDATE - Modifier un livre
    @PutMapping("/{id}")
    public ResponseEntity<Livre> updateLivre(@PathVariable Long id, @RequestBody Livre livreDetails) {
        Livre livre = livreRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Livre non trouvé"));

        Optional<Categorie> categorieOpt = categorieRepository.findById(livreDetails.getCategorie().getId());

        if (!categorieOpt.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);  // Catégorie invalide
        }

        livre.setTitre(livreDetails.getTitre());
        livre.setAuteur(livreDetails.getAuteur());
        livre.setPrix(livreDetails.getPrix());
        livre.setCategorie(categorieOpt.get());

        Livre updatedLivre = livreRepository.save(livre);
        return ResponseEntity.ok(updatedLivre);
    }

    // DELETE - Supprimer un livre
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteLivre(@PathVariable Long id) {
        try {
            livreRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

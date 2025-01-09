package com.librarie.livre_service.controller;

import com.librarie.livre_service.model.dto.LivreUpdateDTO;
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
    public ResponseEntity<List<Livre>> getAllLivres() {
        List<Livre> livres = livreRepository.findAll();
        if (livres.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(livres, HttpStatus.OK);
    }

    // CREATE - Ajouter un nouveau livre
    @PostMapping
    public ResponseEntity<Livre> createLivre(@RequestBody Livre livre) {
        if (livre.getCategorie() == null || livre.getCategorie().getId() == null) {
            // Vous pouvez créer un objet Livre avec des valeurs vides ou renvoyer une réponse d'erreur plus appropriée
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);  // Retourne BAD_REQUEST pour signaler l'erreur
        }

        Optional<Categorie> categorieOpt = categorieRepository.findById(livre.getCategorie().getId());
        if (!categorieOpt.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);  // Catégorie introuvable
        }

        livre.setCategorie(categorieOpt.get());
        Livre savedLivre = livreRepository.save(livre);
        return new ResponseEntity<>(savedLivre, HttpStatus.CREATED);
    }

    // READ - Afficher un livre par ID
    @GetMapping("/{id}")
    public ResponseEntity<Livre> getLivreById(@PathVariable Long id) {
        Optional<Livre> livreOpt = livreRepository.findById(id);
        if (livreOpt.isPresent()) {
            Livre livre = livreOpt.get();
            return new ResponseEntity<>(livre, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // Retourne NOT_FOUND si livre non trouvé
        }
    }

    // READ - Afficher les livres par catégorie
    @GetMapping("/categorie/{categorieId}")
    public ResponseEntity<List<Livre>> getLivresByCategorie(@PathVariable Long categorieId) {
        Optional<Categorie> categorieOpt = categorieRepository.findById(categorieId);
        if (!categorieOpt.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // Catégorie introuvable
        }

        List<Livre> livres = livreRepository.findByCategorie(categorieOpt.get());
        if (livres.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);  // Aucune donnée disponible
        }
        return new ResponseEntity<>(livres, HttpStatus.OK);
    }

    // UPDATE - Modifier un livre
    @PutMapping("/{id}")
    public ResponseEntity<Livre> updateLivre(@PathVariable Long id, @RequestBody LivreUpdateDTO livreUpdateDTO) {
        Optional<Livre> existingLivre = livreRepository.findById(id);
        if (!existingLivre.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // Livre introuvable
        }

        Livre livre = existingLivre.get();
        if (livreUpdateDTO.getCategorieId() != null) {
            Optional<Categorie> categorieOpt = categorieRepository.findById(livreUpdateDTO.getCategorieId());
            if (!categorieOpt.isPresent()) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);  // Catégorie introuvable
            }
            livre.setCategorie(categorieOpt.get());
        }

        livre.setTitre(livreUpdateDTO.getTitre());
        livre.setAuteur(livreUpdateDTO.getAuteur());
        livre.setPrix(livreUpdateDTO.getPrix());

        livreRepository.save(livre);
        return new ResponseEntity<>(livre, HttpStatus.OK);
    }

    // DELETE - Supprimer un livre
    @DeleteMapping("/{id}")
    public ResponseEntity<Livre> deleteLivre(@PathVariable Long id) {
        try {
            if (!livreRepository.existsById(id)) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // Livre introuvable
            }

            livreRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);  // Livre supprimé avec succès
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);  // Erreur interne du serveur
        }
    }

    // DELETE - Supprimer tous les livres
    @DeleteMapping
    public ResponseEntity<String> deleteAllLivres() {
        try {
            livreRepository.deleteAll();  // Supprime tous les livres
            return new ResponseEntity<>("Tous les livres ont été supprimés avec succès.", HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>("Erreur lors de la suppression des livres.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

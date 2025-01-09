package com.librarie.livre_service.controller;

import com.librarie.livre_service.model.entities.Categorie;
import com.librarie.livre_service.repository.CategorieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategorieController {

    @Autowired
    private CategorieRepository categorieRepository;

    // Afficher toutes les catégories
    @GetMapping
    public List<Categorie> getAllCategories() {
        return categorieRepository.findAll();
    }

    // Ajouter une nouvelle catégorie
    @PostMapping
    public ResponseEntity<Categorie> createCategorie(@RequestBody Categorie categorie) {
        Categorie savedCategorie = categorieRepository.save(categorie);
        return new ResponseEntity<>(savedCategorie, HttpStatus.CREATED);
    }

    // Afficher une catégorie par ID
    @GetMapping("/{id}")
    public ResponseEntity<Categorie> getCategorieById(@PathVariable Long id) {
        return categorieRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Mettre à jour une catégorie
    @PutMapping("/{id}")
    public ResponseEntity<Categorie> updateCategorie(@PathVariable Long id, @RequestBody Categorie categorieDetails) {
        Categorie categorie = categorieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Catégorie non trouvée"));

        categorie.setNom(categorieDetails.getNom());
        categorie.setDescription(categorieDetails.getDescription());
        categorie.setImage(categorieDetails.getImage());

        Categorie updatedCategorie = categorieRepository.save(categorie);
        return ResponseEntity.ok(updatedCategorie);
    }

    // Supprimer une catégorie
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteCategorie(@PathVariable Long id) {
        try {
            if (categorieRepository.existsById(id)) {
                categorieRepository.deleteById(id);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);  // Catégorie supprimée avec succès
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // Catégorie non trouvée
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);  // Erreur lors de la suppression
        }
    }

    // Supprimer toutes les catégories
    @DeleteMapping
    public ResponseEntity<String> deleteAllCategories() {
        try {
            categorieRepository.deleteAll();  // Supprimer toutes les catégories
            return new ResponseEntity<>("Toutes les catégories ont été supprimées avec succès.", HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>("Erreur lors de la suppression des catégories.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

package com.librarie.livre_service.controller;

import com.librarie.livre_service.model.Livre;
import com.librarie.livre_service.repository.LivreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/livres")
public class LivreControler {
    @Autowired
    private LivreRepository livreRepository;
    @GetMapping
    public List<Livre> getAllivres() {
        return livreRepository.findAll();
    }
    @PostMapping
    public ResponseEntity<Livre> CreateLivre(@RequestBody Livre livre){
        Livre SavedLivre =livreRepository.save(livre);
        return  new ResponseEntity<>(SavedLivre, HttpStatus.CREATED);
    }
                @GetMapping("/{id}")
    public  ResponseEntity<Livre> getLivreById(@PathVariable Long id) {
                    Optional<Livre> livre = livreRepository.findById((id));
                    return livre.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                            .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));


                }
/*@PutMapping("/{id}")
    public ResponseEntity<Livre> up*/
    }





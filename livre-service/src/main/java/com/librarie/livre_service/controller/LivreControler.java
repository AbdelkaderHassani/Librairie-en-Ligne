package com.librarie.livre_service.controller;

import com.librarie.livre_service.model.Livre;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
@RestController
@RequestMapping("/api/livres")
public class LivreControler {
    @GetMapping
    public List<Livre> getAllivres(){
        return Arrays.asList(
                new Livre(1L,"Spring Boot en Action","Craig Walls"),
                new Livre(2L,"Java Concurrency in Practice","Brian Goetz")
        );

    }
    }




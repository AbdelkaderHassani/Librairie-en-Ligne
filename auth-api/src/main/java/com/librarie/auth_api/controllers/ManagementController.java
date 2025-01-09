package com.librarie.auth_api.controllers;

import com.librarie.auth_api.dtos.UserDto;
import com.librarie.auth_api.entities.User;
import com.librarie.auth_api.services.ManagementService;
import com.librarie.auth_api.services.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/management")
@RestController
public class ManagementController {

    private static final Logger logger = LoggerFactory.getLogger(ManagementController.class);

    private final ManagementService managementService;
    private final JwtService jwtService;

    public ManagementController(ManagementService managementService, JwtService jwtService) {
        this.managementService = managementService;
        this.jwtService = jwtService;
    }

    // Obtenir un utilisateur par ID
    @GetMapping("/user/{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long userId) {
        try {
            UserDto userDto = managementService.getUserById(userId);
            return ResponseEntity.ok(userDto);
        } catch (Exception e) {
            logger.error("Erreur lors de la récupération de l'utilisateur : " + e.getMessage(), e);
            return ResponseEntity.status(500).body(null); // Internal error
        }
    }

    // Mettre à jour les informations d'un utilisateur
    @PutMapping("/update/{userId}")
    public ResponseEntity<String> updateUser(@PathVariable Long userId, @RequestBody UserDto userDto) {
        try {
            managementService.updateUser(userId, userDto);
            return ResponseEntity.ok("Utilisateur mis à jour avec succès.");
        } catch (Exception e) {
            logger.error("Erreur lors de la mise à jour de l'utilisateur : " + e.getMessage(), e);
            return ResponseEntity.status(500).body("Erreur interne lors de la mise à jour.");
        }
    }

    // Supprimer un utilisateur
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
        try {
            managementService.deleteUser(userId);
            return ResponseEntity.ok("Utilisateur supprimé avec succès.");
        } catch (Exception e) {
            logger.error("Erreur lors de la suppression de l'utilisateur : " + e.getMessage(), e);
            return ResponseEntity.status(500).body("Erreur interne lors de la suppression.");
        }
    }

    // Obtenir la liste des utilisateurs
    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        try {
            List<UserDto> users = managementService.getAllUsers();
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            logger.error("Erreur lors de la récupération des utilisateurs : " + e.getMessage(), e);
            return ResponseEntity.status(500).body(null); // Internal server error
        }
    }
}

package com.librarie.auth_api.controllers;

import com.librarie.auth_api.dtos.AssignRoleDto;
import com.librarie.auth_api.dtos.UserDto;
import com.librarie.auth_api.entities.User;
import com.librarie.auth_api.services.AdminService;
import com.librarie.auth_api.services.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/admin")
@RestController
public class AdminController {

    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    private final AdminService adminService;
    private final JwtService jwtService;

    public AdminController(AdminService adminService, JwtService jwtService) {
        this.adminService = adminService;
        this.jwtService = jwtService;
    }

    // Obtenir la liste des utilisateurs
    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getUsers() {
        try {
            List<UserDto> users = adminService.getAllUsers();
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            logger.error("Erreur lors de la récupération des utilisateurs : " + e.getMessage(), e);
            return ResponseEntity.status(500).body(null); // Internal server error
        }
    }

    // Activer un utilisateur
    @PutMapping("/activate/{userId}")
    public ResponseEntity<String> activateUser(@PathVariable Long userId) {
        try {
            adminService.activateUser(userId);
            return ResponseEntity.ok("Utilisateur activé avec succès.");
        } catch (Exception e) {
            logger.error("Erreur lors de l'activation de l'utilisateur : " + e.getMessage(), e);
            return ResponseEntity.status(500).body("Erreur interne lors de l'activation.");
        }
    }

    // Désactiver un utilisateur
    @PutMapping("/deactivate/{userId}")
    public ResponseEntity<String> deactivateUser(@PathVariable Long userId) {
        try {
            adminService.deactivateUser(userId);
            return ResponseEntity.ok("Utilisateur désactivé avec succès.");
        } catch (Exception e) {
            logger.error("Erreur lors de la désactivation de l'utilisateur : " + e.getMessage(), e);
            return ResponseEntity.status(500).body("Erreur interne lors de la désactivation.");
        }
    }

    // Assigner un rôle à un utilisateur
    @PostMapping("/assign-role")
    public ResponseEntity<String> assignRole(@RequestBody AssignRoleDto assignRoleDto) {
        try {
            adminService.assignRole(assignRoleDto);
            return ResponseEntity.ok("Rôle assigné avec succès.");
        } catch (Exception e) {
            logger.error("Erreur lors de l'assignation du rôle : " + e.getMessage(), e);
            return ResponseEntity.status(500).body("Erreur interne lors de l'assignation du rôle.");
        }
    }

    // Retirer un rôle d'un utilisateur
    @DeleteMapping("/remove-role")
    public ResponseEntity<String> removeRole(@RequestBody AssignRoleDto assignRoleDto) {
        try {
            adminService.removeRole(assignRoleDto);
            return ResponseEntity.ok("Rôle retiré avec succès.");
        } catch (Exception e) {
            logger.error("Erreur lors du retrait du rôle : " + e.getMessage(), e);
            return ResponseEntity.status(500).body("Erreur interne lors du retrait du rôle.");
        }
    }
}

package com.librarie.auth_api.controllers;

import com.librarie.auth_api.dtos.*;
import com.librarie.auth_api.entities.User;
import com.librarie.auth_api.exceptions.InvalidCredentialsException;
import com.librarie.auth_api.exceptions.InvalidPasswordException; // Import de l'exception personnalisée
import com.librarie.auth_api.exceptions.UserNotFoundException;
import com.librarie.auth_api.services.AuthenticationService;
import com.librarie.auth_api.services.JwtService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    private final JwtService jwtService;
    private final AuthenticationService authenticationService;

    // Constructeur
    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    // Méthode d'inscription
    @PostMapping("/signup")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterUserDto registerUserDto, BindingResult bindingResult) {
        // Vérification des erreurs de validation avant de procéder à l'inscription
        if (bindingResult.hasErrors()) {
            // Si des erreurs de validation sont trouvées, on renvoie un message d'erreur avec les détails
            String errorMessage = bindingResult.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.joining(", "));
            return ResponseEntity.badRequest().body("Erreur de validation : " + errorMessage);
        }

        try {
            // Valider le mot de passe avant l'inscription
            authenticationService.validatePassword(registerUserDto.getMotDePasse());

            // Inscription de l'utilisateur
            User registeredUser = authenticationService.signup(registerUserDto);

            // Envoi d'un email de confirmation
            authenticationService.sendConfirmationEmail(registeredUser);

            // Retourner un message de succès
            return ResponseEntity.ok("Utilisateur inscrit avec succès!");

        } catch (InvalidPasswordException e) {
            // Gérer l'exception InvalidPasswordException
            logger.error("Erreur lors de l'inscription : " + e.getMessage(), e);
            return ResponseEntity.badRequest().body("Erreur : " + e.getMessage());
        } catch (IllegalArgumentException e) {
            // Gérer l'exception IllegalArgumentException (données invalides)
            logger.error("Erreur lors de l'inscription : " + e.getMessage(), e);
            return ResponseEntity.badRequest().body("Erreur : Données invalides.");
        } catch (Exception e) {
            // Gérer toute autre exception générique
            logger.error("Erreur interne lors de l'inscription : " + e.getMessage(), e);
            return ResponseEntity.status(500).body("Erreur interne lors de l'inscription.");
        }
        
    }


    // Méthode de connexion (Authentification)
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@Valid @RequestBody LoginUserDto loginUserDto) {
        try {
            // Authenticate user
            User authenticatedUser = authenticationService.authenticate(loginUserDto);
            String jwtToken = jwtService.generateToken(authenticatedUser);
            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setToken(jwtToken);
            loginResponse.setExpiresIn(jwtService.getExpirationTime());

            return ResponseEntity.ok(loginResponse);
        } catch (InvalidCredentialsException | UserNotFoundException e) {
            logger.error("Erreur d'authentification : " + e.getMessage(), e);  // Log the error with stack trace
            return ResponseEntity.status(401).body(null);  // Unauthorized
        } catch (Exception e) {
            logger.error("Erreur interne lors de l'authentification : " + e.getMessage(), e);  // Log the error with stack trace
            return ResponseEntity.status(500).body(null);  // Internal error
        }
    }

    // Confirmer l'email de l'utilisateur via le token
    @GetMapping("/confirm-email")
    public ResponseEntity<String> confirmEmail(@RequestParam String token) {
        try {
            authenticationService.confirmEmail(token);  // Vérifie et confirme l'email en utilisant le token
            return ResponseEntity.ok("Email confirmé avec succès.");
        } catch (Exception e) {
            logger.error("Erreur lors de la confirmation de l'email : " + e.getMessage(), e);  // Log the error with stack trace
            return ResponseEntity.status(400).body("Token de confirmation invalide ou expiré.");
        }
    }

    // Demander un lien pour réinitialiser le mot de passe
    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@Valid @RequestBody ForgotPasswordDto forgotPasswordDto) {
        try {
            authenticationService.sendPasswordResetEmail(forgotPasswordDto.getEmail());  // Envoi d'un email pour réinitialiser le mot de passe
            return ResponseEntity.ok("Email de réinitialisation du mot de passe envoyé.");
        } catch (Exception e) {
            logger.error("Erreur lors de l'envoi de l'email de réinitialisation : " + e.getMessage(), e);  // Log the error with stack trace
            return ResponseEntity.status(400).body("Erreur lors de l'envoi de l'email.");
        }
    }

    // Réinitialiser le mot de passe en utilisant un token
    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestParam String token, @Valid @RequestBody ResetPasswordDto resetPasswordDto) {
        try {
            authenticationService.resetPassword(token, resetPasswordDto.getNewPassword());  // Réinitialiser le mot de passe
            return ResponseEntity.ok("Mot de passe réinitialisé avec succès.");
        } catch (Exception e) {
            logger.error("Erreur lors de la réinitialisation du mot de passe : " + e.getMessage(), e);  // Log the error with stack trace
            return ResponseEntity.status(400).body("Token invalide ou expiré.");
        }
    }
}

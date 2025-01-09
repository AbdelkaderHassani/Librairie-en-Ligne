package com.librarie.auth_api.services;

import com.librarie.auth_api.dtos.*;
import com.librarie.auth_api.entities.EnumRole;
import com.librarie.auth_api.entities.User;
import com.librarie.auth_api.exceptions.InvalidCredentialsException;
import com.librarie.auth_api.exceptions.InvalidEmailException;
import com.librarie.auth_api.exceptions.InvalidPasswordException;
import com.librarie.auth_api.exceptions.UserNotFoundException;
import com.librarie.auth_api.repository.UserRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Pattern;

@Service
public class AuthenticationService {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JavaMailSender emailSender;
    private final Validator validator;

    private static final String PASSWORD_PATTERN = "^(?=(.*\\d.*){3})(?=(.*[a-z].*){2})(?=(.*[A-Z].*){2})(?=(.*[\\W_].*){1}).{8,}$";
    private static final String EMAIL_PATTERN = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";  // Expression régulière pour valider l'email

    @Autowired
    public AuthenticationService(
            UserRepository userRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder,
            JavaMailSender emailSender
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailSender = emailSender;

        // Initialisation du validateur
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    // Inscription
    public User signup(RegisterUserDto input) throws InvalidPasswordException, InvalidEmailException {
        // Validation de l'email
        validateEmail(input.getEmail());

        // Validation des entrées via les annotations sur le DTO
        Set<ConstraintViolation<RegisterUserDto>> violations = validator.validate(input);
        if (!violations.isEmpty()) {
            for (ConstraintViolation<RegisterUserDto> violation : violations) {
                logger.error("Violation: " + violation.getMessage());
            }
            throw new IllegalArgumentException("Email invalide ou vide.");
        }

        // Vérifier si l'email est déjà utilisé
        if (userRepository.findByEmail(input.getEmail()).isPresent()) {
            throw new IllegalArgumentException("L'email est déjà utilisé.");
        }

        // Vérifier si le mot de passe et la confirmation du mot de passe sont identiques
        if (input.getMotDePasse() == null || input.getMotDePasse().isEmpty()) {
            throw new IllegalArgumentException("Le mot de passe ne peut pas être vide.");
        }

        // Validation du mot de passe
        validatePassword(input.getMotDePasse());

        if (!input.getMotDePasse().equals(input.getRetypePassword())) {
            throw new IllegalArgumentException("Les mots de passe ne correspondent pas.");
        }

        // Créer l'entité utilisateur
        User user = new User();
        user.setNom(input.getNom());
        user.setPrenom(input.getPrenom());
        user.setEmail(input.getEmail());
        user.setMotDePasse(passwordEncoder.encode(input.getMotDePasse()));

        user.setGender(input.getGender());
        // Générer et définir le token de confirmation
        String confirmationToken = UUID.randomUUID().toString();
        user.setConfirmationToken(confirmationToken);

        // Assignation du rôle USER par défaut
        user.setRoles(Set.of(EnumRole.USER));
        // Définir les dates de création et de mise à jour
        Date now = new Date();
        user.setCreatedAt(now);
        user.setUpdatedAt(now);

        // Sauvegarder et retourner l'utilisateur
        return userRepository.save(user);
    }

    // Authentification
    public User authenticate(LoginUserDto input) throws InvalidCredentialsException, UserNotFoundException {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            input.getEmail(),
                            input.getMotDePasse()
                    )
            );
        } catch (Exception e) {
            throw new InvalidCredentialsException("Email ou mot de passe incorrect.");
        }

        return userRepository.findByEmail(input.getEmail())
                .orElseThrow(() -> new UserNotFoundException("Utilisateur non trouvé avec cet email"));
    }

    // Envoi de l'email de confirmation
    public void sendConfirmationEmail(User user) {
        String confirmationToken = UUID.randomUUID().toString();
        user.setConfirmationToken(confirmationToken);
        userRepository.save(user);

        String confirmationLink = "http://localhost:8005/auth/confirm-email?token=" + confirmationToken;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("hassaniabdelkaderhh@gmail.com");
        message.setTo(user.getEmail());
        message.setSubject("Confirmez votre adresse email");
        message.setText("Cliquez sur ce lien pour confirmer votre adresse email : " + confirmationLink);

        emailSender.send(message);
    }

    // Confirmer l'email
    public void confirmEmail(String token) {
        User user = userRepository.findByConfirmationToken(token)
                .orElseThrow(() -> new RuntimeException("Token de confirmation invalide"));

        user.setEmailVerified(true);
        user.setConfirmationToken(null);  // Supprimer le token après confirmation
        userRepository.save(user);
    }

    // Envoi de l'email de réinitialisation du mot de passe
    public void sendPasswordResetEmail(String email) throws UserNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Utilisateur non trouvé"));

        String resetToken = UUID.randomUUID().toString();
        user.setResetToken(resetToken);
        user.setResetTokenExpiration(new Date(System.currentTimeMillis() + 3600000)); // Token expire dans 1 heure
        userRepository.save(user);

        String resetLink = "http://localhost:8005/auth/reset-password?token=" + resetToken;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("hassaniabdelkaderhh@gmail.com");
        message.setTo(email);
        message.setSubject("Réinitialisation du mot de passe");
        message.setText("Cliquez sur ce lien pour réinitialiser votre mot de passe : " + resetLink);

        emailSender.send(message);
    }

    // Réinitialiser le mot de passe
    public void resetPassword(String token, String newPassword) throws InvalidPasswordException {
        User user = userRepository.findByResetToken(token)
                .orElseThrow(() -> new RuntimeException("Token de réinitialisation invalide"));

        if (user.getResetTokenExpiration().before(new Date())) {
            throw new RuntimeException("Le token de réinitialisation a expiré");
        }

        validatePassword(newPassword);

        user.setMotDePasse(passwordEncoder.encode(newPassword));
        user.setResetToken(null);  // Supprimer le token après réinitialisation
        user.setResetTokenExpiration(null);  // Supprimer la date d'expiration
        userRepository.save(user);
    }



    // Mettre à jour un utilisateur à partir d'un DTO
    public User updateUserFromDto(UpdateUserDto dto, User user) {
        if (dto.getNom() != null) user.setNom(dto.getNom());
        if (dto.getPrenom() != null) user.setPrenom(dto.getPrenom());
        if (dto.getEmail() != null) user.setEmail(dto.getEmail());
        user.setUpdatedAt(new Date());
        return userRepository.save(user);
    }

    // Méthode pour valider le mot de passe
    public void validatePassword(String password) throws InvalidPasswordException {
        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        if (!pattern.matcher(password).matches()) {
            throw new InvalidPasswordException("Le mot de passe doit contenir au moins 8 caractères, " +
                    "avec au moins trois chiffres, deux lettres majuscules, deux lettres minuscules et un caractère spécial.");
        }
    }

    // Méthode pour valider l'email
    private void validateEmail(String email) throws InvalidEmailException {
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        if (!pattern.matcher(email).matches()) {
            throw new InvalidEmailException("L'email doit contenir au moins 2 chiffres," + "avec @ ");
        }
    }

}

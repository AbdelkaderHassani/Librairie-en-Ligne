package com.librarie.auth_api.exceptions;

import java.util.regex.Pattern;

public class Validator {

    // Validation des mots de passe
    private static final String PASSWORD_PATTERN =
            "^(?=(.*\\d.*){3})(?=(.*[A-Z].*){2})(?=(.*[a-z].*){2})(?=(.*[\\W_].*){1}).{8,}$";

    // Validation des e-mails (RFC standard avec au moins 2 chiffres)
    private static final String EMAIL_PATTERN =
            "^(?=.*\\d.*\\d)[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

    // Méthode pour valider le mot de passe
    public static void validatePassword(String password) throws InvalidPasswordException {
        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        if (!pattern.matcher(password).matches()) {
            throw new InvalidPasswordException("Le mot de passe doit contenir au moins 8 caractères, " +
                    "avec au moins 3 chiffres, 2 lettres majuscules, 2 lettres minuscules et 1 caractère spécial.");
        }
    }

    // Méthode pour valider l'e-mail
    public static void validateEmail(String email) throws InvalidEmailException {
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        if (!pattern.matcher(email).matches()) {
            throw new InvalidEmailException("L'e-mail doit être valide et contenir au moins 2 chiffres.");
        }
    }

    // Méthode principale pour tester les validations
    public static void main(String[] args) {
        String password = "Test123!"; // Exemple de mot de passe
        String email = "user12@example.com"; // Exemple d'e-mail

        try {
            validatePassword(password);
            System.out.println("Mot de passe valide");
        } catch (InvalidPasswordException e) {
            System.out.println("Erreur mot de passe : " + e.getMessage());
        }

        try {
            validateEmail(email);
            System.out.println("E-mail valide");
        } catch (InvalidEmailException e) {
            System.out.println("Erreur e-mail : " + e.getMessage());
        }
    }
}

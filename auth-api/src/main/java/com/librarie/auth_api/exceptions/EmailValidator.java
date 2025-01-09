package com.librarie.auth_api.exceptions;

import java.util.regex.Pattern;

public class EmailValidator {
    // Regex pour valider les e-mails (au moins 2 chiffres et un format classique d'email)
    private static final String EMAIL_PATTERN = "^(?=.*\\d.*\\d)[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

    // Méthode pour valider l'e-mail
    public static boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        return pattern.matcher(email).matches();
    }

    // Méthode pour lancer une exception si l'email est invalide
    public static void validateEmail(String email) throws InvalidEmailException {
        if (!isValidEmail(email)) {
            throw new InvalidEmailException("L'e-mail doit être valide et contenir au moins 2 chiffres, avec @ .");
        }
    }
}
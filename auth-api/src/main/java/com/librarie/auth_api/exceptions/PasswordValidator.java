package com.librarie.auth_api.exceptions;

import java.util.regex.Pattern;

public class PasswordValidator {

    // Validation des mots de passe
    private static final String PASSWORD_PATTERN =
            "^(?=(.*\\d.*){3})(?=(.*[A-Z].*){2})(?=(.*[a-z].*){2})(?=(.*[\\W_].*){1}).{8,}$";


    // Méthode pour valider le mot de passe
    public static void validatePassword(String password) throws InvalidPasswordException {
        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        if (!pattern.matcher(password).matches()) {
            throw new InvalidPasswordException("Le mot de passe doit contenir au moins 8 caractères, " +
                    "avec au moins 3 chiffres, 2 lettres majuscules, 2 lettres minuscules et 1 caractère spécial.");
        }
    }



    // Méthode principale pour tester les validations
    public static void main(String[] args) {
        String password = ""; // Exemple de mot de passe

        try {
            validatePassword(password);
            System.out.println("Mot de passe valide");
        } catch (InvalidPasswordException e) {
            System.out.println("Erreur mot de passe : " + e.getMessage());
        }


    }
}

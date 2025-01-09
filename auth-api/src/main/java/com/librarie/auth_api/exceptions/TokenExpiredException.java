package com.librarie.auth_api.exceptions;

public class TokenExpiredException extends RuntimeException {

    // Constructeur sans argument
    public TokenExpiredException() {
        super("Le token a expiré.");
    }

    // Constructeur avec message personnalisé
    public TokenExpiredException(String message) {
        super(message);
    }

    // Constructeur avec message personnalisé et cause
    public TokenExpiredException(String message, Throwable cause) {
        super(message, cause);
    }

    // Constructeur avec cause seulement
    public TokenExpiredException(Throwable cause) {
        super(cause);
    }
}

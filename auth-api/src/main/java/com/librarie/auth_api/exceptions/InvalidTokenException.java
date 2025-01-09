package com.librarie.auth_api.exceptions;

public class InvalidTokenException extends RuntimeException {

    // Constructeur avec message personnalisé
    public InvalidTokenException(String message) {
        super(message);
    }

    // Constructeur avec message et cause
    public InvalidTokenException(String message, Throwable cause) {
        super(message, cause);
    }

    // Constructeur avec cause seulement
    public InvalidTokenException(Throwable cause) {
        super(cause);
    }
}

package com.librarie.auth_api.exceptions;

public class RoleNotFoundException extends RuntimeException {

    // Constructeur avec message personnalisé
    public RoleNotFoundException(String message) {
        super(message);
    }

    // Constructeur avec message personnalisé et cause
    public RoleNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    // Constructeur avec cause seulement
    public RoleNotFoundException(Throwable cause) {
        super(cause);
    }
}

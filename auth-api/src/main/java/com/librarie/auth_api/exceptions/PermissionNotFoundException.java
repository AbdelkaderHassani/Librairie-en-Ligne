package com.librarie.auth_api.exceptions;

public class PermissionNotFoundException extends RuntimeException {

    // Constructeur sans message
    public PermissionNotFoundException() {
        super("Permission non trouvée");
    }

    // Constructeur avec un message personnalisé
    public PermissionNotFoundException(String message) {
        super(message);
    }

    // Constructeur avec message et cause
    public PermissionNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    // Constructeur avec la cause
    public PermissionNotFoundException(Throwable cause) {
        super(cause);
    }
}

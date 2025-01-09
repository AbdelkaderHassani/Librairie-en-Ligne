package com.librarie.auth_api.exceptions;

// Vous n'avez pas besoin de cet import
// import static jdk.javadoc.internal.doclets.formats.html.markup.HtmlStyle.exceptions;

public class InvalidPasswordException extends Exception { // Étendre Exception

    // Constructeur avec message
    public InvalidPasswordException(String message) {
        super(message);
    }
}

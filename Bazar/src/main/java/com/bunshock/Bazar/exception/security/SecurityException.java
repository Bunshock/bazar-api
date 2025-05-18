package com.bunshock.Bazar.exception.security;


public class SecurityException extends RuntimeException {

    public SecurityException(String action, String message) {
        super("Error de seguridad :: (" + action + "): " + message);
    }
    
}

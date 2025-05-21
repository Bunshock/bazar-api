package com.bunshock.Bazar.exception.security;

import org.springframework.security.core.AuthenticationException;


public class JwtAuthFilterException extends AuthenticationException {

    public JwtAuthFilterException(String message) {
        super("Fallo al procesar filtro de seguridad: " + message);
    }
    
}

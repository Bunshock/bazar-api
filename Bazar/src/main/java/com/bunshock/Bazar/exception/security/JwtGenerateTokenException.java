package com.bunshock.Bazar.exception.security;

import org.springframework.security.core.AuthenticationException;


public class JwtGenerateTokenException extends AuthenticationException {

    public JwtGenerateTokenException(String message) {
        super("Fallo al generar token: " + message);
    }
    
}

package com.bunshock.Bazar.exception.security;

import org.springframework.security.core.AuthenticationException;


public class JwtInvalidTokenException extends AuthenticationException {

    public JwtInvalidTokenException(String message) {
        super("Fallo en validación de token: " + message);
    }
    
}

package com.bunshock.Bazar.exception.security;


public class JwtGenerateTokenException extends SecurityUnauthorizedException {

    public JwtGenerateTokenException(String message) {
        super("fallo al generar token: " + message);
    }
    
}

package com.bunshock.Bazar.exception.security;


public class JwtInvalidTokenException extends SecurityUnauthorizedException {

    public JwtInvalidTokenException(String message) {
        super("fallo en validación de token: " + message);
    }
    
}

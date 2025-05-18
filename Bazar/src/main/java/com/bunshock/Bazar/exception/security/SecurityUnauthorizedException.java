package com.bunshock.Bazar.exception.security;


public class SecurityUnauthorizedException extends SecurityException {

    public SecurityUnauthorizedException(String message) {
        super("autenticación", message);
    }
    
}

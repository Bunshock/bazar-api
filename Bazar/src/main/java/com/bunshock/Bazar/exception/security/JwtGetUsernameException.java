package com.bunshock.Bazar.exception.security;


public class JwtGetUsernameException extends SecurityUnauthorizedException {

    public JwtGetUsernameException(String message) {
        super("fallo al obtener username desde token: " + message);
    }
    
}

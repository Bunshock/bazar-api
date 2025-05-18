package com.bunshock.Bazar.exception.security;


public class NotAuthenticatedException extends SecurityUnauthorizedException {
    
    public NotAuthenticatedException() {
        super("usuario no autenticado");
    }
    
}

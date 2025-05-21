package com.bunshock.Bazar.exception.security;

import org.springframework.security.core.AuthenticationException;


public class NotAuthenticatedException extends AuthenticationException {
    
    public NotAuthenticatedException() {
        super("Usuario no autenticado");
    }
    
}

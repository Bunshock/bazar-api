package com.bunshock.Bazar.exception.security;

import org.springframework.security.core.AuthenticationException;


public class JwtGetUsernameException extends AuthenticationException {

    public JwtGetUsernameException(String message) {
    super("Fallo al obtener username desde token: " + message);
    }
    
}

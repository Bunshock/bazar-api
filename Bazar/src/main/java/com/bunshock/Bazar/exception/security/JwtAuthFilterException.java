package com.bunshock.Bazar.exception.security;


public class JwtAuthFilterException extends SecurityInternalServerException {

    public JwtAuthFilterException(String message) {
        super("fallo al procesar filtro de seguridad: " + message);
    }
    
}

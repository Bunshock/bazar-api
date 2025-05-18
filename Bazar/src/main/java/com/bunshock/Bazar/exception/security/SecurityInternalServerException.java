package com.bunshock.Bazar.exception.security;


public class SecurityInternalServerException extends SecurityException {

    public SecurityInternalServerException(String message) {
        super("servidor", message);
    }
    
}

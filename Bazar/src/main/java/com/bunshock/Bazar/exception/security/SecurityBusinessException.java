package com.bunshock.Bazar.exception.security;


public class SecurityBusinessException extends SecurityException {

    public SecurityBusinessException(String action, String message) {
        super(action, message);
    }
    
}

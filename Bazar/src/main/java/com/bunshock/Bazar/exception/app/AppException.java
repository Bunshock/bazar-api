package com.bunshock.Bazar.exception.app;


public class AppException extends RuntimeException {

    public AppException(String action, String message) {
        super("Error al " + action + ": " + message);
    }
    
}

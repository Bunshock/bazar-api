package com.bunshock.Bazar.exception.app;


public class AppNotFoundException extends AppException {

    public AppNotFoundException(String action, String entity, String identifier, Long id) {
        super(action, "no se encontró " + entity + " con " + identifier + " " + id);
    }
    
}

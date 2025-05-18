package com.bunshock.Bazar.exception.security;


public class SecurityNotFoundException extends SecurityException {

    public SecurityNotFoundException(String action, String entity, String identifier, Long id) {
        super(action, "no se encontró " + entity + " con " + identifier + " " + id);
    }
    
    // Sobrecarga: para entidades como RoleEntity
    public SecurityNotFoundException(String action, String entity, String identifier, String name) {
        super(action, "no se encontró " + entity + " con " + identifier + " " + name);
    }
    
}

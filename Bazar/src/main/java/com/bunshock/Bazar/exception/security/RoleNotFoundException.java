package com.bunshock.Bazar.exception.security;


public class RoleNotFoundException extends SecurityNotFoundException {

    public RoleNotFoundException(String action, String role) {
        super(action, "rol", "nombre", role);
    }
    
}

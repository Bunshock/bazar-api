package com.bunshock.Bazar.exception.security;


public class RegisterDuplicateUsernameException extends SecurityBusinessException {

    public RegisterDuplicateUsernameException(String username) {
        super("registrar usuario", "el usuario '" + username + "' ya está registrado");
    }
    
}

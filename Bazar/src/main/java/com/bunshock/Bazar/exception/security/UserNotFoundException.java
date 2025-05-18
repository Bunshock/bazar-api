package com.bunshock.Bazar.exception.security;


public class UserNotFoundException extends SecurityNotFoundException {

    public UserNotFoundException(String action, String username) {
        super(action, "usuario", "username", username);
    }
    
}

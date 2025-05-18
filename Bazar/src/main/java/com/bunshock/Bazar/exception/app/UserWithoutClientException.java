package com.bunshock.Bazar.exception.app;


public class UserWithoutClientException extends AppBusinessException {

    public UserWithoutClientException(String action, String username) {
        super(action, "el usuario " + username + "no tiene un cliente asociado");
    }
    
}

package com.bunshock.Bazar.exception.app;


public class AppBusinessException extends AppException {

    public AppBusinessException(String action, String message) {
        super(action, message);
    }
    
}

package com.bunshock.Bazar.exception.app;


public class ClientNotFoundException extends AppNotFoundException {

    public ClientNotFoundException(String action, Long id_client) {
        super(action, "cliente", "id", id_client);
    }
    
}

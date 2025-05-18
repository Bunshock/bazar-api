package com.bunshock.Bazar.utils.mapper;

import com.bunshock.Bazar.dto.client.InputClientDTO;
import com.bunshock.Bazar.dto.client.ShowClientDTO;
import com.bunshock.Bazar.model.Client;


public class ClientMapper {
    
    public static ShowClientDTO ClientToShowClientDTO(Client client) {
        
        // Un cliente puede no tener un usuario asociado
        Long userId = null;
        if (client.getUser() != null) userId = client.getUser().getId();
        
        return new ShowClientDTO(
                client.getIdClient(),
                client.getFirstName(),
                client.getLastName(),
                client.getDni(),
                userId
        );
    }
    
    public static Client updateClientFromDTO(Client client, InputClientDTO editedClient) {
        if (editedClient.getFirstName() != null)
            client.setFirstName(editedClient.getFirstName());
        if (editedClient.getLastName() != null)
            client.setLastName(editedClient.getLastName());
        if (editedClient.getDni() != null)
            client.setDni(editedClient.getDni());
        return client;
    }
    
}

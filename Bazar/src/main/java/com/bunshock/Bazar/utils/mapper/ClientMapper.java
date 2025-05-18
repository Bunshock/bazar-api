package com.bunshock.Bazar.utils.mapper;

import com.bunshock.Bazar.dto.client.ClientDTO;
import com.bunshock.Bazar.model.Client;


public class ClientMapper {
    
    public static ClientDTO ClientToClientDTO(Client client) {
        return new ClientDTO(
                client.getFirstName(),
                client.getLastName(),
                client.getDni()
        );
    }
    
    public static Client updateClientFromDTO(Client client, ClientDTO editedClient) {
        if (editedClient.getFirstName() != null)
            client.setFirstName(editedClient.getFirstName());
        if (editedClient.getLastName() != null)
            client.setLastName(editedClient.getLastName());
        if (editedClient.getDni() != null)
            client.setDni(editedClient.getDni());
        return client;
    }
    
}

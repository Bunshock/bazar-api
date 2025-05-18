package com.bunshock.Bazar.utils.mapper;

import com.bunshock.Bazar.dto.client.InputClientDTO;
import com.bunshock.Bazar.dto.client.ShowClientDTO;
import com.bunshock.Bazar.model.Client;


public class ClientMapper {
    
    public static ShowClientDTO ClientToShowClientDTO(Client client) {
        return new ShowClientDTO(
                client.getIdClient(),
                client.getFirstName(),
                client.getLastName(),
                client.getDni(),
                client.getUser().getId()
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

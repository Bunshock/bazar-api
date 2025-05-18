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
    
}

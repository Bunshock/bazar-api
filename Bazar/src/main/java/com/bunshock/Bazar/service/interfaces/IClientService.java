package com.bunshock.Bazar.service.interfaces;

import com.bunshock.Bazar.dto.client.InputClientDTO;
import com.bunshock.Bazar.model.Client;
import java.util.List;


public interface IClientService {
    
    // CRUD Cliente
    void saveClient(InputClientDTO inputClient);
    List<Client> getClients();
    Client getClientById(Long id_client);
    void deleteClient(Long id_client);
    Client editClient(Long id_client, InputClientDTO editedClient);
    
    // Operaciones de USER
    Client getMyClient();
    Client editMyClient(InputClientDTO editedClient);
    
}

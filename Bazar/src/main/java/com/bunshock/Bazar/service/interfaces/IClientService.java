package com.bunshock.Bazar.service.interfaces;

import com.bunshock.Bazar.dto.client.ClientDTO;
import java.util.List;


public interface IClientService {
    
    // CRUD Cliente
    void saveClient(ClientDTO inputClient);
    List<ClientDTO> getClients();
    ClientDTO getClientById(Long id_client);
    void deleteClient(Long id_client);
    ClientDTO editClient(Long id_client, ClientDTO editedClient);
    
    // Operaciones de USER
    ClientDTO getMyClient();
    ClientDTO editMyClient(ClientDTO editedClient);
    
}

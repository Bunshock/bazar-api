package com.bunshock.Bazar.service.interfaces;

import com.bunshock.Bazar.dto.client.ClientDTO;
import java.util.List;


public interface IClientService {
    
    // CRUD Cliente
    void saveCliente(ClientDTO datosCliente);
    List<ClientDTO> getClientes();
    ClientDTO getClienteById(Long id);
    void deleteCliente(Long id);
    ClientDTO editCliente(Long id, ClientDTO datosEditados);
    
    // Operaciones de USER
    ClientDTO getMiCliente();
    ClientDTO editarMiCliente(ClientDTO clienteEditado);
    
}

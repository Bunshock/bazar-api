package com.bunshock.Bazar.service;

import com.bunshock.Bazar.dto.ClienteDTO;
import com.bunshock.Bazar.model.Cliente;
import java.util.List;


public interface IClienteService {
    
    // CRUD Cliente
    void saveCliente(ClienteDTO datosCliente);
    List<Cliente> getClientes();
    Cliente getClienteById(Long id);
    void deleteCliente(Long id);
    Cliente editCliente(Long id, ClienteDTO datosEditados);
    
}

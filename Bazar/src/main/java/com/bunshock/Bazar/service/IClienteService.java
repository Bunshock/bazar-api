package com.bunshock.Bazar.service;

import com.bunshock.Bazar.dto.ClienteSimpleDTO;
import java.util.List;


public interface IClienteService {
    
    // CRUD Cliente
    void saveCliente(ClienteSimpleDTO datosCliente);
    List<ClienteSimpleDTO> getClientes();
    ClienteSimpleDTO getClienteById(Long id);
    void deleteCliente(Long id);
    ClienteSimpleDTO editCliente(Long id, ClienteSimpleDTO datosEditados);
    
    // Operaciones de USER
    ClienteSimpleDTO getMiCliente();
    ClienteSimpleDTO editarMiCliente(ClienteSimpleDTO clienteEditado);
    
}

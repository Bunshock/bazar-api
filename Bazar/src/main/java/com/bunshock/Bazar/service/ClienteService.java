package com.bunshock.Bazar.service;

import com.bunshock.Bazar.dto.ClienteDTO;
import com.bunshock.Bazar.model.Cliente;
import com.bunshock.Bazar.repository.IClienteRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ClienteService implements IClienteService {
    
    private final IClienteRepository clienteRepository;

    @Autowired
    public ClienteService(IClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }    

    @Override
    public void saveCliente(ClienteDTO datosCliente) {
        Cliente cliente = new Cliente();
        
        cliente.setNombre(datosCliente.getNombre());
        cliente.setApellido(datosCliente.getApellido());
        cliente.setDni(datosCliente.getDni());
        
        clienteRepository.save(cliente);
    }

    @Override
    public List<Cliente> getClientes() {
        return clienteRepository.findAll();
    }

    @Override
    public Cliente getClienteById(Long id) {
        return clienteRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteCliente(Long id) {
        clienteRepository.deleteById(id);
    }

    @Override
    public Cliente editCliente(Long id, ClienteDTO clienteEditado) {
        Cliente cliente = this.getClienteById(id);
        
        if (clienteEditado.getNombre() != null)
            cliente.setNombre(clienteEditado.getNombre());
        if (clienteEditado.getApellido() != null)
            cliente.setApellido(clienteEditado.getApellido());
        if (clienteEditado.getDni() != null)
            cliente.setDni(clienteEditado.getDni());
        
        return clienteRepository.save(cliente);
    }
    
}

package com.bunshock.Bazar.service.impl;

import com.bunshock.Bazar.dto.client.ClientDTO;
import com.bunshock.Bazar.model.Client;
import com.bunshock.Bazar.model.UserEntity;
import com.bunshock.Bazar.repository.IUserRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.bunshock.Bazar.repository.IClientRepository;
import com.bunshock.Bazar.service.interfaces.IClientService;


@Service
public class ClientService implements IClientService {
    
    private final IClientRepository clienteRepository;
    private final IUserRepository userRepository;

    @Autowired
    public ClientService(IClientRepository clienteRepository,
            IUserRepository userRepository) {
        this.clienteRepository = clienteRepository;
        this.userRepository = userRepository;
    }    

    @Override
    public void saveCliente(ClientDTO datosCliente) {
        Client cliente = new Client();
        
        cliente.setNombre(datosCliente.getNombre());
        cliente.setApellido(datosCliente.getApellido());
        cliente.setDni(datosCliente.getDni());
        
        clienteRepository.save(cliente);
    }

    @Override
    public List<ClientDTO> getClientes() {
        List<Client> listaClientes = clienteRepository.findAll();
        
        return listaClientes.stream()
                .map(cliente -> {
                    return new ClientDTO(
                            cliente.getNombre(),
                            cliente.getApellido(),
                            cliente.getDni()
                    );
                })
                .collect(Collectors.toList());
    }

    @Override
    public ClientDTO getClienteById(Long id) {
        Client cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("El usuario con "
                        + "id (" + id + ") no fue encontrado"));
        
        return new ClientDTO(
                cliente.getNombre(),
                cliente.getApellido(),
                cliente.getDni()
        );
    }

    @Override
    public void deleteCliente(Long id) {
        clienteRepository.deleteById(id);
    }

    @Override
    public ClientDTO editCliente(Long id, ClientDTO clienteEditado) {
        Client cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("El usuario con "
                        + "id (" + id + ") no fue encontrado"));
        
        if (clienteEditado.getNombre() != null)
            cliente.setNombre(clienteEditado.getNombre());
        if (clienteEditado.getApellido() != null)
            cliente.setApellido(clienteEditado.getApellido());
        if (clienteEditado.getDni() != null)
            cliente.setDni(clienteEditado.getDni());
        
        cliente = clienteRepository.save(cliente);
        
        return new ClientDTO(
                cliente.getNombre(),
                cliente.getApellido(),
                cliente.getDni()
        );
    }

    @Override
    public ClientDTO getMiCliente() {
        // Obtenemos el usuario relacionado al cliente logueado
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("El usuario con "
                        + "username (" + username + ") no existe"));
        
        Client cliente = clienteRepository.findById(user.getCliente().getIdCliente())
                .orElseThrow(() -> new EntityNotFoundException("El usuario con "
                        + "username (" + username + ") no tiene cliente asociado"));
        
        return new ClientDTO(
                cliente.getNombre(),
                cliente.getApellido(),
                cliente.getDni()
        );
    }
    
    @Override
    public ClientDTO editarMiCliente(ClientDTO clienteEditado) {
        // Obtenemos el usuario relacionado al cliente logueado
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("El usuario con "
                        + "username (" + username + ") no existe"));
        
        Client cliente = user.getCliente();
        
        if (clienteEditado.getNombre() != null)
            cliente.setNombre(clienteEditado.getNombre());
        if (clienteEditado.getApellido() != null)
            cliente.setApellido(clienteEditado.getApellido());
        if (clienteEditado.getDni() != null)
            cliente.setDni(clienteEditado.getDni());
        
        cliente = clienteRepository.save(cliente);
        
        return new ClientDTO(
                cliente.getNombre(),
                cliente.getApellido(),
                cliente.getDni()
        );
    }
    
}

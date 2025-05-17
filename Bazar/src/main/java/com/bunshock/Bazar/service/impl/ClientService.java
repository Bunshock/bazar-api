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
    
    private final IClientRepository clientRepository;
    private final IUserRepository userRepository;

    @Autowired
    public ClientService(IClientRepository clientRepository,
            IUserRepository userRepository) {
        this.clientRepository = clientRepository;
        this.userRepository = userRepository;
    }    

    @Override
    public void saveClient(ClientDTO inputClient) {
        Client client = new Client();
        
        client.setFirstName(inputClient.getFirstName());
        client.setLastName(inputClient.getLastName());
        client.setDni(inputClient.getDni());
        
        clientRepository.save(client);
    }

    @Override
    public List<ClientDTO> getClients() {
        List<Client> clientList = clientRepository.findAll();
        
        return clientList.stream()
                .map(cliente -> {
                    return new ClientDTO(
                            cliente.getFirstName(),
                            cliente.getLastName(),
                            cliente.getDni()
                    );
                })
                .collect(Collectors.toList());
    }

    @Override
    public ClientDTO getClientById(Long id_client) {
        Client client = clientRepository.findById(id_client)
                .orElseThrow(() -> new EntityNotFoundException("El usuario con "
                        + "id (" + id_client + ") no fue encontrado"));
        
        return new ClientDTO(
                client.getFirstName(),
                client.getLastName(),
                client.getDni()
        );
    }

    @Override
    public void deleteClient(Long id_client) {
        clientRepository.deleteById(id_client);
    }

    @Override
    public ClientDTO editClient(Long id_client, ClientDTO editedClient) {
        Client client = clientRepository.findById(id_client)
                .orElseThrow(() -> new EntityNotFoundException("El usuario con "
                        + "id (" + id_client + ") no fue encontrado"));
        
        if (editedClient.getFirstName() != null)
            client.setFirstName(editedClient.getFirstName());
        if (editedClient.getLastName() != null)
            client.setLastName(editedClient.getLastName());
        if (editedClient.getDni() != null)
            client.setDni(editedClient.getDni());
        
        client = clientRepository.save(client);
        
        return new ClientDTO(
                client.getFirstName(),
                client.getLastName(),
                client.getDni()
        );
    }

    @Override
    public ClientDTO getMyClient() {
        // Obtenemos el usuario relacionado al cliente logueado
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("El usuario con "
                        + "username (" + username + ") no existe"));
        
        Client client = clientRepository.findById(user.getCliente().getIdClient())
                .orElseThrow(() -> new EntityNotFoundException("El usuario con "
                        + "username (" + username + ") no tiene cliente asociado"));
        
        return new ClientDTO(
                client.getFirstName(),
                client.getLastName(),
                client.getDni()
        );
    }
    
    @Override
    public ClientDTO editMyClient(ClientDTO editedClient) {
        // Obtenemos el usuario relacionado al cliente logueado
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("El usuario con "
                        + "username (" + username + ") no existe"));
        
        Client client = user.getCliente();
        
        if (editedClient.getFirstName() != null)
            client.setFirstName(editedClient.getFirstName());
        if (editedClient.getLastName() != null)
            client.setLastName(editedClient.getLastName());
        if (editedClient.getDni() != null)
            client.setDni(editedClient.getDni());
        
        client = clientRepository.save(client);
        
        return new ClientDTO(
                client.getFirstName(),
                client.getLastName(),
                client.getDni()
        );
    }
    
}

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
import com.bunshock.Bazar.utils.SecurityUtils;
import com.bunshock.Bazar.utils.mapper.ClientMapper;


@Service
public class ClientService implements IClientService {
    
    private final IClientRepository clientRepository;
    private final IUserRepository userRepository;

    @Autowired
    public ClientService(IClientRepository clientRepository, IUserRepository userRepository) {
        this.clientRepository = clientRepository;
        this.userRepository = userRepository;
    }
    
    private Client getClientIfExists(Long id_client) {
        return clientRepository.findById(id_client)
                .orElseThrow(() -> new EntityNotFoundException("El usuario con "
                        + "id (" + id_client + ") no fue encontrado"));
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
                .map(client -> ClientMapper.ClientToClientDTO(client))
                .collect(Collectors.toList());
    }

    @Override
    public ClientDTO getClientById(Long id_client) {
        Client client = getClientIfExists(id_client);
        return ClientMapper.ClientToClientDTO(client);
    }

    @Override
    public void deleteClient(Long id_client) {
        clientRepository.deleteById(id_client);
    }

    @Override
    public ClientDTO editClient(Long id_client, ClientDTO editedClient) {
        Client client = getClientIfExists(id_client);
        
        if (editedClient.getFirstName() != null)
            client.setFirstName(editedClient.getFirstName());
        if (editedClient.getLastName() != null)
            client.setLastName(editedClient.getLastName());
        if (editedClient.getDni() != null)
            client.setDni(editedClient.getDni());
        
        client = clientRepository.save(client);
        
        return ClientMapper.ClientToClientDTO(client);
    }

    @Override
    public ClientDTO getMyClient() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("El usuario con "
                        + "username (" + username + ") no existe"));
        
        Client client = getClientIfExists(user.getClient().getIdClient());
        
        return ClientMapper.ClientToClientDTO(client);
    }
    
    @Override
    public ClientDTO editMyClient(ClientDTO editedClient) {
        String username = SecurityUtils.getLoggedUsername();
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("El usuario con "
                        + "username (" + username + ") no existe"));
        
        Client client = user.getClient();
        
        if (editedClient.getFirstName() != null)
            client.setFirstName(editedClient.getFirstName());
        if (editedClient.getLastName() != null)
            client.setLastName(editedClient.getLastName());
        if (editedClient.getDni() != null)
            client.setDni(editedClient.getDni());
        
        client = clientRepository.save(client);
        
        return ClientMapper.ClientToClientDTO(client);
    }
    
}

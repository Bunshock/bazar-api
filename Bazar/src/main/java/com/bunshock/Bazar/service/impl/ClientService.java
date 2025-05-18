package com.bunshock.Bazar.service.impl;

import com.bunshock.Bazar.dto.client.InputClientDTO;
import com.bunshock.Bazar.exception.app.ClientNotFoundException;
import com.bunshock.Bazar.exception.app.UserWithoutClientException;
import com.bunshock.Bazar.exception.security.UserNotFoundException;
import com.bunshock.Bazar.model.Client;
import com.bunshock.Bazar.model.UserEntity;
import com.bunshock.Bazar.repository.IUserRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Override
    public void saveClient(InputClientDTO inputClient) {
        Client client = new Client();
        
        client.setFirstName(inputClient.getFirstName());
        client.setLastName(inputClient.getLastName());
        client.setDni(inputClient.getDni());
        
        clientRepository.save(client);
    }

    @Override
    public List<Client> getClients() {
        return clientRepository.findAll();
    }

    @Override
    public Client getClientById(Long id_client) {
        return clientRepository.findById(id_client)
                .orElseThrow(() -> new ClientNotFoundException("obtener cliente", id_client));
    }

    @Override
    public void deleteClient(Long id_client) {
        clientRepository.deleteById(id_client);
    }

    @Override
    public Client editClient(Long id_client, InputClientDTO editedClient) {
        Client client = this.getClientById(id_client);
        return clientRepository.save(ClientMapper.updateClientFromDTO(client, editedClient));
    }

    @Override
    public Client getMyClient() {
        String username = SecurityUtils.getLoggedUsername();
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("obtener mi cliente", username));
        
        if (user.getClient() == null) throw new UserWithoutClientException("obtener mi cliente", username);
        
        return this.getClientById(user.getClient().getIdClient());
    }
    
    @Override
    public Client editMyClient(InputClientDTO editedClient) {
        String username = SecurityUtils.getLoggedUsername();
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("editar mi cliente", username));
        
        if (user.getClient() == null) throw new UserWithoutClientException("editar mi cliente", username);
        
        return this.editClient(user.getClient().getIdClient(), editedClient);
    }
    
}

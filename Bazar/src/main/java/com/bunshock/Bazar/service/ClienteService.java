package com.bunshock.Bazar.service;

import com.bunshock.Bazar.dto.ClienteSimpleDTO;
import com.bunshock.Bazar.model.Cliente;
import com.bunshock.Bazar.model.UserEntity;
import com.bunshock.Bazar.repository.IClienteRepository;
import com.bunshock.Bazar.repository.IUserRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class ClienteService implements IClienteService {
    
    private final IClienteRepository clienteRepository;
    private final IUserRepository userRepository;

    @Autowired
    public ClienteService(IClienteRepository clienteRepository,
            IUserRepository userRepository) {
        this.clienteRepository = clienteRepository;
        this.userRepository = userRepository;
    }    

    @Override
    public void saveCliente(ClienteSimpleDTO datosCliente) {
        Cliente cliente = new Cliente();
        
        cliente.setNombre(datosCliente.getNombre());
        cliente.setApellido(datosCliente.getApellido());
        cliente.setDni(datosCliente.getDni());
        
        clienteRepository.save(cliente);
    }

    @Override
    public List<ClienteSimpleDTO> getClientes() {
        List<Cliente> listaClientes = clienteRepository.findAll();
        
        return listaClientes.stream()
                .map(cliente -> {
                    return new ClienteSimpleDTO(
                            cliente.getNombre(),
                            cliente.getApellido(),
                            cliente.getDni()
                    );
                })
                .collect(Collectors.toList());
    }

    @Override
    public ClienteSimpleDTO getClienteById(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("El usuario con "
                        + "id (" + id + ") no fue encontrado"));
        
        return new ClienteSimpleDTO(
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
    public ClienteSimpleDTO editCliente(Long id, ClienteSimpleDTO clienteEditado) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("El usuario con "
                        + "id (" + id + ") no fue encontrado"));
        
        if (clienteEditado.getNombre() != null)
            cliente.setNombre(clienteEditado.getNombre());
        if (clienteEditado.getApellido() != null)
            cliente.setApellido(clienteEditado.getApellido());
        if (clienteEditado.getDni() != null)
            cliente.setDni(clienteEditado.getDni());
        
        cliente = clienteRepository.save(cliente);
        
        return new ClienteSimpleDTO(
                cliente.getNombre(),
                cliente.getApellido(),
                cliente.getDni()
        );
    }

    @Override
    public ClienteSimpleDTO getMiCliente() {
        // Obtenemos el usuario relacionado al cliente logueado
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("El usuario con "
                        + "username (" + username + ") no existe"));
        
        Cliente cliente = clienteRepository.findById(user.getCliente().getId_cliente())
                .orElseThrow(() -> new EntityNotFoundException("El usuario con "
                        + "username (" + username + ") no tiene cliente asociado"));
        
        return new ClienteSimpleDTO(
                cliente.getNombre(),
                cliente.getApellido(),
                cliente.getDni()
        );
    }
    
    @Override
    public ClienteSimpleDTO editarMiCliente(ClienteSimpleDTO clienteEditado) {
        // Obtenemos el usuario relacionado al cliente logueado
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("El usuario con "
                        + "username (" + username + ") no existe"));
        
        Cliente cliente = user.getCliente();
        
        if (clienteEditado.getNombre() != null)
            cliente.setNombre(clienteEditado.getNombre());
        if (clienteEditado.getApellido() != null)
            cliente.setApellido(clienteEditado.getApellido());
        if (clienteEditado.getDni() != null)
            cliente.setDni(clienteEditado.getDni());
        
        cliente = clienteRepository.save(cliente);
        
        return new ClienteSimpleDTO(
                cliente.getNombre(),
                cliente.getApellido(),
                cliente.getDni()
        );
    }
    
}

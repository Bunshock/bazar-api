package com.bunshock.Bazar.controller;

import com.bunshock.Bazar.dto.client.InputClientDTO;
import com.bunshock.Bazar.dto.OnCreate;
import com.bunshock.Bazar.dto.OnUpdate;
import com.bunshock.Bazar.dto.client.ShowClientDTO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.bunshock.Bazar.service.interfaces.IClientService;
import com.bunshock.Bazar.exception.ValidationHandler;
import com.bunshock.Bazar.model.Client;
import com.bunshock.Bazar.utils.mapper.ClientMapper;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/clientes")
@PreAuthorize("hasRole('ADMIN')")
public class ClientController {
    
    private final IClientService clientService;

    @Autowired
    public ClientController(IClientService clientService) {
        this.clientService = clientService;
    }
    
    @PostMapping("/crear")
    public ResponseEntity<?> createClient(@Validated(OnCreate.class) @RequestBody InputClientDTO inputClient,
            BindingResult bindingResult) {
        
        if (bindingResult.hasErrors())
            return ValidationHandler.handleValidationErrors(bindingResult);
        
        clientService.saveClient(inputClient);
        return new ResponseEntity<>("Cliente creado satisfactoriamente", HttpStatus.CREATED);
    }
    
    @GetMapping("")
    public ResponseEntity<List<ShowClientDTO>> getAllClients() {
        List<Client> clientList = clientService.getClients();
        return new ResponseEntity<>(clientList.stream()
                .map(client -> ClientMapper.ClientToShowClientDTO(client))
                .collect(Collectors.toList()),
                HttpStatus.OK);
    }
    
    @GetMapping("/{id_client}")
    public ResponseEntity<ShowClientDTO> getOneClient(@PathVariable Long id_client) {
        Client client = clientService.getClientById(id_client);
        return new ResponseEntity<>(ClientMapper.ClientToShowClientDTO(client), HttpStatus.OK);
    }
    
    @DeleteMapping("/eliminar/{id_client}")
    public ResponseEntity<String> deleteClient(@PathVariable Long id_client) {
        clientService.deleteClient(id_client);
        return new ResponseEntity<>("Cliente borrado exitosamente", HttpStatus.OK);
    }
    
    @PutMapping("/editar/{id_client}")
    public ResponseEntity<ShowClientDTO> editClient(@PathVariable Long id_client,
            @Validated(OnUpdate.class) @RequestBody InputClientDTO editedClient, BindingResult bindingResult) {
        
        if (bindingResult.hasErrors())
            return ValidationHandler.handleValidationErrors(bindingResult);
        
        Client updatedClient = clientService.editClient(id_client, editedClient);
        return new ResponseEntity<>(ClientMapper.ClientToShowClientDTO(updatedClient), HttpStatus.OK);
    }
    
    // Operaciones de usuarios con rol : USER
    
    @GetMapping("/mi-cliente")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ShowClientDTO> getMyClient() {
        Client myClient = clientService.getMyClient();
        return new ResponseEntity<>(ClientMapper.ClientToShowClientDTO(myClient), HttpStatus.OK);
    }
    
    @PutMapping("/editar/mi-cliente")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ShowClientDTO> editMyClient(
            @Validated(OnUpdate.class) @RequestBody InputClientDTO editedClient,
            BindingResult bindingResult) {
        
        if (bindingResult.hasErrors())
            return ValidationHandler.handleValidationErrors(bindingResult);

        Client updatedClient = clientService.editMyClient(editedClient);
        return new ResponseEntity<>(ClientMapper.ClientToShowClientDTO(updatedClient), HttpStatus.OK);
    }
    
}

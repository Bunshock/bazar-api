package com.bunshock.Bazar.controller;

import com.bunshock.Bazar.dto.client.ClientDTO;
import com.bunshock.Bazar.dto.OnCreate;
import com.bunshock.Bazar.dto.OnUpdate;
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
    public ResponseEntity<?> createClient(@Validated(OnCreate.class) @RequestBody ClientDTO inputClient,
            BindingResult bindingResult) {
        
        if (bindingResult.hasErrors())
            return ValidationHandler.handleValidationErrors(bindingResult);
        
        clientService.saveClient(inputClient);
        return new ResponseEntity<>("Cliente creado satisfactoriamente", HttpStatus.CREATED);
    }
    
    @GetMapping("")
    public ResponseEntity<List<ClientDTO>> getAllClients() {
        return new ResponseEntity<>(clientService.getClients(), HttpStatus.OK);
    }
    
    @GetMapping("/{id_client}")
    public ResponseEntity<ClientDTO> getOneClient(@PathVariable Long id_client) {
        return new ResponseEntity<>(clientService.getClientById(id_client),
                HttpStatus.OK);
    }
    
    @DeleteMapping("/eliminar/{id_client}")
    public ResponseEntity<String> deleteClient(@PathVariable Long id_client) {
        clientService.deleteClient(id_client);
        return new ResponseEntity<>("Cliente borrado exitosamente", HttpStatus.OK);
    }
    
    @PutMapping("/editar/{id_client}")
    public ResponseEntity<ClientDTO> editClient(@PathVariable Long id_client,
            @Validated(OnUpdate.class) @RequestBody ClientDTO editedClient, BindingResult bindingResult) {
        
        if (bindingResult.hasErrors())
            return ValidationHandler.handleValidationErrors(bindingResult);
        
        return new ResponseEntity<>(clientService.editClient(id_client, editedClient),
                HttpStatus.OK);
    }
    
    @GetMapping("/mi-cliente")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ClientDTO> getMyClient() {
        return new ResponseEntity<>(clientService.getMyClient(), HttpStatus.OK);
    }
    
    @PutMapping("/editar/mi-cliente")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ClientDTO> editMyClient(
            @Validated(OnUpdate.class) @RequestBody ClientDTO editedClient,
            BindingResult bindingResult) {
        
        if (bindingResult.hasErrors())
            return ValidationHandler.handleValidationErrors(bindingResult);

        return new ResponseEntity<>(clientService.editMyClient(editedClient),
                HttpStatus.OK);
    }
    
}

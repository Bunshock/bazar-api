package com.bunshock.Bazar.controller;

import com.bunshock.Bazar.dto.ApiResponseDTO;
import com.bunshock.Bazar.dto.ApiSuccessResponseDTO;
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
import com.bunshock.Bazar.utils.mapper.ClientMapper;
import java.time.LocalDateTime;
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
    public ResponseEntity<ApiResponseDTO> createClient(
            @Validated(OnCreate.class) @RequestBody InputClientDTO inputClient,
            BindingResult bindingResult) {
        
        if (bindingResult.hasErrors())
            return ValidationHandler.handleValidationErrors(bindingResult);
        
        clientService.saveClient(inputClient);
        
        return new ResponseEntity<>(ApiSuccessResponseDTO.<Void>builder()
                .status(HttpStatus.CREATED.value())
                .message("Cliente creado correctamente")
                .timestamp(LocalDateTime.now())
                .data(null)
                .build(), HttpStatus.CREATED);
    }
    
    @GetMapping("")
    public ResponseEntity<ApiResponseDTO> getAllClients() {
        return new ResponseEntity<>(ApiSuccessResponseDTO.<List<ShowClientDTO>>builder()
                .status(HttpStatus.OK.value())
                .message("Lista de clientes obtenida correctamente")
                .timestamp(LocalDateTime.now())
                .data(clientService.getClients().stream()
                        .map(client -> ClientMapper.ClientToShowClientDTO(client))
                        .collect(Collectors.toList()))
                .build(), HttpStatus.OK);
    }
    
    @GetMapping("/{id_client}")
    public ResponseEntity<ApiResponseDTO> getOneClient(
            @PathVariable Long id_client) {
        return new ResponseEntity<>(ApiSuccessResponseDTO.<ShowClientDTO>builder()
                .status(HttpStatus.OK.value())
                .message("Cliente obtenido correctamente")
                .timestamp(LocalDateTime.now())
                .data(ClientMapper.ClientToShowClientDTO(
                        clientService.getClientById(id_client)))
                .build(), HttpStatus.OK);
    }
    
    @DeleteMapping("/eliminar/{id_client}")
    public ResponseEntity<ApiResponseDTO> deleteClient(
            @PathVariable Long id_client) {
        clientService.deleteClient(id_client);
        return new ResponseEntity<>(ApiSuccessResponseDTO.<Void>builder()
                .status(HttpStatus.OK.value())
                .message("Cliente borrado correctamente")
                .timestamp(LocalDateTime.now())
                .data(null)
                .build(), HttpStatus.OK);
    }
    
    @PutMapping("/editar/{id_client}")
    public ResponseEntity<ApiResponseDTO> editClient(
            @PathVariable Long id_client,
            @Validated(OnUpdate.class) @RequestBody InputClientDTO editedClient,
            BindingResult bindingResult) {
        
        if (bindingResult.hasErrors())
            return ValidationHandler.handleValidationErrors(bindingResult);
        
        return new ResponseEntity<>(ApiSuccessResponseDTO.<ShowClientDTO>builder()
                .status(HttpStatus.OK.value())
                .message("Cliente editado correctamente")
                .timestamp(LocalDateTime.now())
                .data(ClientMapper.ClientToShowClientDTO(
                        clientService.editClient(id_client, editedClient)))
                .build(), HttpStatus.OK);
    }
    
    // Operaciones de usuarios con rol : USER
    
    @GetMapping("/mi-cliente")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ApiResponseDTO> getMyClient() {
        return new ResponseEntity<>(ApiSuccessResponseDTO.<ShowClientDTO>builder()
                .status(HttpStatus.OK.value())
                .message("Cliente de usuario obtenido correctamente")
                .timestamp(LocalDateTime.now())
                .data(ClientMapper.ClientToShowClientDTO(
                        clientService.getMyClient()))
                .build(), HttpStatus.OK);
    }
    
    @PutMapping("/editar/mi-cliente")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ApiResponseDTO> editMyClient(
            @Validated(OnUpdate.class) @RequestBody InputClientDTO editedClient,
            BindingResult bindingResult) {
        
        if (bindingResult.hasErrors())
            return ValidationHandler.handleValidationErrors(bindingResult);

        return new ResponseEntity<>(ApiSuccessResponseDTO.<ShowClientDTO>builder()
                .status(HttpStatus.OK.value())
                .message("Cliente de usuario editado correctamente")
                .timestamp(LocalDateTime.now())
                .data(ClientMapper.ClientToShowClientDTO(
                        clientService.editMyClient(editedClient)))
                .build(), HttpStatus.OK);
    }
    
}

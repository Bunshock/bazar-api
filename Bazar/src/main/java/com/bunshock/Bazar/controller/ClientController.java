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
import com.bunshock.Bazar.exception.GlobalExceptionHandler;


@RestController
@RequestMapping("/clientes")
@PreAuthorize("hasRole('ADMIN')")
public class ClientController {
    
    private final IClientService clienteService;
    private final GlobalExceptionHandler controllerUtils;

    @Autowired
    public ClientController(IClientService clienteService,
            GlobalExceptionHandler controllerUtils) {
        this.clienteService = clienteService;
        this.controllerUtils = controllerUtils;
    }
    
    @PostMapping("/crear")
    public ResponseEntity<?> crearCliente(@Validated(OnCreate.class) @RequestBody ClientDTO datosCliente,
            BindingResult bindingResult) {
        
        if (bindingResult.hasErrors())
            return controllerUtils.handleValidationErrors(bindingResult);
        
        clienteService.saveCliente(datosCliente);
        return new ResponseEntity<>("Cliente creado satisfactoriamente", HttpStatus.CREATED);
    }
    
    @GetMapping("")
    public ResponseEntity<List<ClientDTO>> traerClientes() {
        return new ResponseEntity<>(clienteService.getClientes(), HttpStatus.OK);
    }
    
    @GetMapping("/{id_cliente}")
    public ResponseEntity<ClientDTO> traerClientePorId(@PathVariable Long id_cliente) {
        return new ResponseEntity<>(clienteService.getClienteById(id_cliente),
                HttpStatus.OK);
    }
    
    @DeleteMapping("/eliminar/{id_cliente}")
    public ResponseEntity<String> borrarCliente(@PathVariable Long id_cliente) {
        clienteService.deleteCliente(id_cliente);
        return new ResponseEntity<>("Cliente borrado exitosamente", HttpStatus.OK);
    }
    
    @PutMapping("/editar/{id_cliente}")
    public ResponseEntity<ClientDTO> editarCliente(@PathVariable Long id_cliente,
            @Validated(OnUpdate.class) @RequestBody ClientDTO clienteEditado, BindingResult bindingResult) {
        
        if (bindingResult.hasErrors())
            return controllerUtils.handleValidationErrors(bindingResult);
        
        return new ResponseEntity<>(clienteService.editCliente(id_cliente,
                clienteEditado), HttpStatus.OK);
    }
    
    @GetMapping("/mi-cliente")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ClientDTO> traerMiCliente() {
        return new ResponseEntity<>(clienteService.getMiCliente(), HttpStatus.OK);
    }
    
    @PutMapping("/editar/mi-cliente")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ClientDTO> editarMiCliente(
            @Validated(OnUpdate.class) @RequestBody ClientDTO clienteEditado,
            BindingResult bindingResult) {
        
        if (bindingResult.hasErrors())
            return controllerUtils.handleValidationErrors(bindingResult);

        return new ResponseEntity<>(clienteService.editarMiCliente(clienteEditado),
                HttpStatus.OK);
    }
    
}

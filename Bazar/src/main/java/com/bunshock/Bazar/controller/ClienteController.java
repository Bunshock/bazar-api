package com.bunshock.Bazar.controller;

import com.bunshock.Bazar.dto.ClienteDTO;
import com.bunshock.Bazar.dto.OnCreate;
import com.bunshock.Bazar.dto.OnUpdate;
import com.bunshock.Bazar.model.Cliente;
import com.bunshock.Bazar.service.IClienteService;
import com.bunshock.Bazar.utils.IControllerUtils;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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


@RestController
@RequestMapping("/clientes")
public class ClienteController {
    
    private final IClienteService clienteService;
    private final IControllerUtils controllerUtils;

    @Autowired
    public ClienteController(IClienteService clienteService,
            IControllerUtils controllerUtils) {
        this.clienteService = clienteService;
        this.controllerUtils = controllerUtils;
    }
    
    @PostMapping("/crear")
    public ResponseEntity<?> crearCliente(@Validated(OnCreate.class) @RequestBody ClienteDTO datosCliente,
            BindingResult bindingResult) {
        
        if (bindingResult.hasErrors())
            return controllerUtils.handleValidationErrors(bindingResult);
        
        clienteService.saveCliente(datosCliente);
        return new ResponseEntity<>("Cliente creado satisfactoriamente", HttpStatus.CREATED);
    }
    
    @GetMapping("")
    public ResponseEntity<List<Cliente>> traerClientes() {
        return new ResponseEntity<>(clienteService.getClientes(), HttpStatus.OK);
    }
    
    @GetMapping("/{id_cliente}")
    public ResponseEntity<Cliente> traerClientePorId(@PathVariable Long id_cliente) {
        return new ResponseEntity<>(clienteService.getClienteById(id_cliente),
                HttpStatus.OK);
    }
    
    @DeleteMapping("/eliminar/{id_cliente}")
    public ResponseEntity<String> borrarCliente(@PathVariable Long id_cliente) {
        clienteService.deleteCliente(id_cliente);
        return new ResponseEntity<>("Cliente borrado exitosamente", HttpStatus.OK);
    }
    
    @PutMapping("/editar/{id_cliente}")
    public ResponseEntity<Cliente> editarCliente(@PathVariable Long id_cliente,
            @Validated(OnUpdate.class) @RequestBody ClienteDTO clienteEditado, BindingResult bindingResult) {
        
        if (bindingResult.hasErrors())
            return controllerUtils.handleValidationErrors(bindingResult);
        
        return new ResponseEntity<>(clienteService.editCliente(id_cliente,
                clienteEditado), HttpStatus.OK);
    }
    
}

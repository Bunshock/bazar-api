package com.bunshock.Bazar.controller;

import com.bunshock.Bazar.dto.ClienteDTO;
import com.bunshock.Bazar.model.Cliente;
import com.bunshock.Bazar.service.IClienteService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @Autowired
    public ClienteController(IClienteService clienteService) {
        this.clienteService = clienteService;
    }
    
    @PostMapping("/crear")
    public ResponseEntity<String> crearCliente(@Valid @RequestBody ClienteDTO datosCliente) {
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
            @Valid @RequestBody ClienteDTO clienteEditado) {
        return new ResponseEntity<>(clienteService.editCliente(id_cliente,
                clienteEditado), HttpStatus.OK);
    }
    
}

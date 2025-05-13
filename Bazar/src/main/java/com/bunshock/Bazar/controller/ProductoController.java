package com.bunshock.Bazar.controller;

import com.bunshock.Bazar.dto.OnCreate;
import com.bunshock.Bazar.dto.OnUpdate;
import com.bunshock.Bazar.dto.ProductoDTO;
import com.bunshock.Bazar.model.Producto;
import com.bunshock.Bazar.service.IProductoService;
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
@RequestMapping("/productos")
public class ProductoController {
    
    private final IProductoService productoService;
    private final IControllerUtils controllerUtils;
    
    @Autowired
    public ProductoController(IProductoService productoService,
            IControllerUtils controllerUtils) {
        this.productoService = productoService;
        this.controllerUtils = controllerUtils;
    }
    
    @PostMapping("/crear")
    public ResponseEntity<?> crearProducto(@Validated(OnCreate.class) @RequestBody ProductoDTO datosProducto,
            BindingResult bindingResult) {
        
        if (bindingResult.hasErrors())
            return controllerUtils.handleValidationErrors(bindingResult);
        
        productoService.saveCliente(datosProducto);
        return new ResponseEntity<>("Producto creado satisfactoriamente", HttpStatus.CREATED);
    }
    
    @GetMapping("")
    public ResponseEntity<List<Producto>> traerProductos() {
        return new ResponseEntity<>(productoService.getProductos(), HttpStatus.OK);
    }
    
    @GetMapping("/{codigo_producto}")
    public ResponseEntity<Producto> traerProductoPorId(@PathVariable Long codigo_producto) {
        return new ResponseEntity<>(productoService.getProductoById(codigo_producto),
                HttpStatus.OK);
    }
    
    @DeleteMapping("/eliminar/{codigo_producto}")
    public ResponseEntity<String> borrarProducto(@PathVariable Long codigo_producto) {
        return new ResponseEntity<>("Producto borrado exitosamente", HttpStatus.OK);
    }
    
    @PutMapping("/editar/{codigo_producto}")
    public ResponseEntity<Producto> editarProducto(@PathVariable Long codigo_producto,
            @Validated(OnUpdate.class) @RequestBody ProductoDTO productoEditado, BindingResult bindingResult) {
        
        if (bindingResult.hasErrors())
            return controllerUtils.handleValidationErrors(bindingResult);
        
        return new ResponseEntity<>(productoService.editProducto(codigo_producto,
                productoEditado), HttpStatus.OK);
    }
    
}

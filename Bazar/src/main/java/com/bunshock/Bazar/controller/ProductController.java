package com.bunshock.Bazar.controller;

import com.bunshock.Bazar.dto.OnCreate;
import com.bunshock.Bazar.dto.OnUpdate;
import com.bunshock.Bazar.dto.product.ProductDTO;
import com.bunshock.Bazar.model.Product;
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
import com.bunshock.Bazar.service.interfaces.IProductService;
import com.bunshock.Bazar.exception.GlobalExceptionHandler;


@RestController
@RequestMapping("/productos")
@PreAuthorize("hasRole('ADMIN')")
public class ProductController {
    
    private final IProductService productoService;
    private final GlobalExceptionHandler controllerUtils;
    
    @Autowired
    public ProductController(IProductService productoService,
            GlobalExceptionHandler controllerUtils) {
        this.productoService = productoService;
        this.controllerUtils = controllerUtils;
    }
    
    @PostMapping("/crear")
    public ResponseEntity<?> crearProducto(@Validated(OnCreate.class) @RequestBody ProductDTO datosProducto,
            BindingResult bindingResult) {
        
        if (bindingResult.hasErrors())
            return controllerUtils.handleValidationErrors(bindingResult);
        
        productoService.saveCliente(datosProducto);
        return new ResponseEntity<>("Producto creado satisfactoriamente", HttpStatus.CREATED);
    }
    
    @GetMapping("")
    @PreAuthorize("permitAll()")
    public ResponseEntity<List<Product>> traerProductos() {
        return new ResponseEntity<>(productoService.getProductos(), HttpStatus.OK);
    }
    
    @GetMapping("/{codigo_producto}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<Product> traerProductoPorId(@PathVariable Long codigo_producto) {
        return new ResponseEntity<>(productoService.getProductoById(codigo_producto),
                HttpStatus.OK);
    }
    
    @DeleteMapping("/eliminar/{codigo_producto}")
    public ResponseEntity<String> borrarProducto(@PathVariable Long codigo_producto) {
        return new ResponseEntity<>("Producto borrado exitosamente", HttpStatus.OK);
    }
    
    @PutMapping("/editar/{codigo_producto}")
    public ResponseEntity<Product> editarProducto(@PathVariable Long codigo_producto,
            @Validated(OnUpdate.class) @RequestBody ProductDTO productoEditado, BindingResult bindingResult) {
        
        if (bindingResult.hasErrors())
            return controllerUtils.handleValidationErrors(bindingResult);
        
        return new ResponseEntity<>(productoService.editProducto(codigo_producto,
                productoEditado), HttpStatus.OK);
    }
    
    @GetMapping("/falta_stock")
    public ResponseEntity<List<Product>> traerProductosFaltaStock() {
        return new ResponseEntity<>(productoService.getLowStockProducts(), HttpStatus.OK);
    }
    
}

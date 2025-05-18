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
import com.bunshock.Bazar.exception.ValidationHandler;
import jakarta.persistence.EntityNotFoundException;


@RestController
@RequestMapping("/productos")
@PreAuthorize("hasRole('ADMIN')")
public class ProductController {
    
    private final IProductService productService;
    
    @Autowired
    public ProductController(IProductService productService) {
        this.productService = productService;
    }
    
    @PostMapping("/crear")
    public ResponseEntity<?> createProduct(@Validated(OnCreate.class) @RequestBody ProductDTO inputProduct,
            BindingResult bindingResult) {
        
        if (bindingResult.hasErrors())
            return ValidationHandler.handleValidationErrors(bindingResult);
        
        productService.saveProduct(inputProduct);
        return new ResponseEntity<>("Producto creado satisfactoriamente", HttpStatus.CREATED);
    }
    
    @GetMapping("")
    @PreAuthorize("permitAll()")
    public ResponseEntity<List<Product>> getAllProducts() {
        return new ResponseEntity<>(productService.getProducts(), HttpStatus.OK);
    }
    
    @GetMapping("/{product_code}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<Product> getOneProduct(@PathVariable Long product_code) {
        return new ResponseEntity<>(productService.getProductByCode(product_code),
                HttpStatus.OK);
    }
    
    @DeleteMapping("/eliminar/{product_code}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long product_code) {
        productService.deleteProduct(product_code);
        return new ResponseEntity<>("Producto borrado exitosamente", HttpStatus.OK);
    }
    
    @PutMapping("/editar/{product_code}")
    public ResponseEntity<?> editProduct(@PathVariable Long product_code,
            @Validated(OnUpdate.class) @RequestBody ProductDTO editedProduct, BindingResult bindingResult) {
        
        if (bindingResult.hasErrors())
            return ValidationHandler.handleValidationErrors(bindingResult);
        
        Product product;
        
        try {
            product = productService.editProduct(product_code, editedProduct);
        } catch(EntityNotFoundException e) {
            return new ResponseEntity<>("Error al editar producto: " + e.getMessage(),
                    HttpStatus.BAD_REQUEST);
        }
        
        return new ResponseEntity<>(product, HttpStatus.OK);
    }
    
    @GetMapping("/falta_stock")
    public ResponseEntity<List<Product>> getLowStockProducts() {
        return new ResponseEntity<>(productService.getLowStockProducts(), HttpStatus.OK);
    }
    
}

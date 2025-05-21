package com.bunshock.Bazar.controller;

import com.bunshock.Bazar.dto.ApiResponseDTO;
import com.bunshock.Bazar.dto.ApiSuccessResponseDTO;
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
import java.time.LocalDateTime;


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
    public ResponseEntity<ApiResponseDTO> createProduct(
            @Validated(OnCreate.class) @RequestBody ProductDTO inputProduct,
            BindingResult bindingResult) {
        
        if (bindingResult.hasErrors())
            return ValidationHandler.handleValidationErrors(bindingResult);
        
        productService.saveProduct(inputProduct);
        
        return new ResponseEntity<>(ApiSuccessResponseDTO.<Void>builder()
                .status(HttpStatus.CREATED.value())
                .message("Producto creado correctamente")
                .timestamp(LocalDateTime.now())
                .data(null)
                .build(), HttpStatus.CREATED);
    }
    
    @GetMapping("")
    @PreAuthorize("permitAll()")
    public ResponseEntity<ApiResponseDTO> getAllProducts() {
        return new ResponseEntity<>(ApiSuccessResponseDTO.<List<Product>>builder()
                .status(HttpStatus.OK.value())
                .message("Lista de productos obtenida correctamente")
                .timestamp(LocalDateTime.now())
                .data(productService.getProducts())
                .build(), HttpStatus.OK);
    }
    
    @GetMapping("/{product_code}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<ApiResponseDTO> getOneProduct(
            @PathVariable Long product_code) {
        return new ResponseEntity<>(ApiSuccessResponseDTO.<Product>builder()
                .status(HttpStatus.OK.value())
                .message("Producto obtenido correctamente")
                .timestamp(LocalDateTime.now())
                .data(productService.getProductByCode(product_code))
                .build(), HttpStatus.OK);
    }
    
    @DeleteMapping("/eliminar/{product_code}")
    public ResponseEntity<ApiResponseDTO> deleteProduct(
            @PathVariable Long product_code) {
        productService.deleteProduct(product_code);
        return new ResponseEntity<>(ApiSuccessResponseDTO.<Void>builder()
                .status(HttpStatus.OK.value())
                .message("Producto borrado correctamente")
                .timestamp(LocalDateTime.now())
                .data(null)
                .build(), HttpStatus.OK);
    }
    
    @PutMapping("/editar/{product_code}")
    public ResponseEntity<ApiResponseDTO> editProduct(
            @PathVariable Long product_code,
            @Validated(OnUpdate.class) @RequestBody ProductDTO editedProduct,
            BindingResult bindingResult) {
        
        if (bindingResult.hasErrors())
            return ValidationHandler.handleValidationErrors(bindingResult);
        
        return new ResponseEntity<>(ApiSuccessResponseDTO.<Product>builder()
                .status(HttpStatus.OK.value())
                .message("Producto editado correctamente")
                .timestamp(LocalDateTime.now())
                .data(productService.editProduct(product_code, editedProduct))
                .build(), HttpStatus.OK);
    }
    
    @GetMapping("/falta_stock")
    public ResponseEntity<ApiResponseDTO> getLowStockProducts() {
        return new ResponseEntity<>(ApiSuccessResponseDTO.<List<Product>>builder()
                .status(HttpStatus.OK.value())
                .message("Lista de productos en falta obtenida correctamente")
                .timestamp(LocalDateTime.now())
                .data(productService.getLowStockProducts())
                .build(), HttpStatus.OK);
    }
    
}

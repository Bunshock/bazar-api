package com.bunshock.Bazar.service.impl;

import com.bunshock.Bazar.dto.product.ProductDTO;
import com.bunshock.Bazar.model.Product;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bunshock.Bazar.repository.IProductRepository;
import com.bunshock.Bazar.service.interfaces.IProductService;
import jakarta.persistence.EntityNotFoundException;


@Service
public class ProductService implements IProductService {
    
    private final IProductRepository productRepository;

    @Autowired
    public ProductService(IProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void saveProduct(ProductDTO inputProduct) {
        Product product = new Product();
        
        product.setName(inputProduct.getName());
        product.setBrand(inputProduct.getBrand());
        product.setPrice(inputProduct.getPrice());
        product.setAmountAvailable(inputProduct.getAmountAvailable());
        
        productRepository.save(product);
    }

    @Override
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductByCode(Long product_code) {
        return productRepository.findById(product_code)
                .orElseThrow(() -> new EntityNotFoundException("No se"
                        + " encontró producto con código: " + product_code));
    }

    @Override
    public void deleteProduct(Long product_code) {
        productRepository.deleteById(product_code);
    }

    @Override
    public Product editProduct(Long product_code, ProductDTO editedProduct) {
        Product product = this.getProductByCode(product_code);
        
        if (editedProduct.getName() != null)
            product.setName(editedProduct.getName());
        if (editedProduct.getBrand()!= null)
            product.setBrand(editedProduct.getBrand());
        if (editedProduct.getPrice()!= null)
            product.setPrice(editedProduct.getPrice());
        if (editedProduct.getAmountAvailable()!= null)
            product.setAmountAvailable(editedProduct.getAmountAvailable());
        
        return productRepository.save(product);
    }

    @Override
    public List<Product> getLowStockProducts() {
        return productRepository.findByAmountAvailableLessThan(5.0);
    }
    
}

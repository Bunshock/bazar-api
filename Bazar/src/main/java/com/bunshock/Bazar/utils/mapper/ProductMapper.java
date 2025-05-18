package com.bunshock.Bazar.utils.mapper;

import com.bunshock.Bazar.dto.product.ProductDTO;
import com.bunshock.Bazar.model.Product;


public class ProductMapper {
    
    public static Product updateProductFromDTO(Product product, ProductDTO editedProduct) {
        if (editedProduct.getName() != null)
            product.setName(editedProduct.getName());
        if (editedProduct.getBrand()!= null)
            product.setBrand(editedProduct.getBrand());
        if (editedProduct.getPrice()!= null)
            product.setPrice(editedProduct.getPrice());
        if (editedProduct.getAmountAvailable()!= null)
            product.setAmountAvailable(editedProduct.getAmountAvailable());
        return product;
    }
    
}

package com.bunshock.Bazar.service.interfaces;

import com.bunshock.Bazar.dto.product.ProductDTO;
import com.bunshock.Bazar.model.Product;
import java.util.List;


public interface IProductService {
    
    // CRUD Producto
    void saveProduct(ProductDTO inputProduct);
    List<Product> getProducts();
    Product getProductByCode(Long product_code);
    void deleteProduct(Long product_code);
    Product editProduct(Long product_code, ProductDTO editedProduct);
    
    List<Product> getLowStockProducts();
    
}

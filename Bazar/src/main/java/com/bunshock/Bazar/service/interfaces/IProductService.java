package com.bunshock.Bazar.service.interfaces;

import com.bunshock.Bazar.dto.product.ProductDTO;
import com.bunshock.Bazar.model.Product;
import java.util.List;


public interface IProductService {
    
    // CRUD Producto
    void saveCliente(ProductDTO datosProducto);
    List<Product> getProductos();
    Product getProductoById(Long id);
    void deleteProducto(Long id);
    Product editProducto(Long id, ProductDTO productoEditado);
    
    List<Product> getLowStockProducts();
    
}

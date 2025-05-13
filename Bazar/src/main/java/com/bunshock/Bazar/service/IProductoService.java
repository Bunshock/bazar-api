package com.bunshock.Bazar.service;

import com.bunshock.Bazar.dto.ProductoDTO;
import com.bunshock.Bazar.model.Producto;
import java.util.List;


public interface IProductoService {
    
    // CRUD Producto
    void saveCliente(ProductoDTO datosProducto);
    List<Producto> getProductos();
    Producto getProductoById(Long id);
    void deleteProducto(Long id);
    Producto editProducto(Long id, ProductoDTO productoEditado);
    
    List<Producto> getLowStockProducts();
    
}

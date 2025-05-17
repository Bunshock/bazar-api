package com.bunshock.Bazar.service.impl;

import com.bunshock.Bazar.dto.product.ProductDTO;
import com.bunshock.Bazar.model.Product;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bunshock.Bazar.repository.IProductRepository;
import com.bunshock.Bazar.service.interfaces.IProductService;


@Service
public class ProductService implements IProductService {
    
    private final IProductRepository productoRepository;

    @Autowired
    public ProductService(IProductRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    @Override
    public void saveCliente(ProductDTO datosProducto) {
        Product producto = new Product();
        
        producto.setNombre(datosProducto.getNombre());
        producto.setMarca(datosProducto.getMarca());
        producto.setCosto(datosProducto.getCosto());
        producto.setCantidadDisponible(datosProducto.getCantidadDisponible());
        
        productoRepository.save(producto);
    }

    @Override
    public List<Product> getProductos() {
        return productoRepository.findAll();
    }

    @Override
    public Product getProductoById(Long id) {
        return productoRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteProducto(Long id) {
        productoRepository.deleteById(id);
    }

    @Override
    public Product editProducto(Long id, ProductDTO productoEditado) {
        Product producto = this.getProductoById(id);
        
        if (productoEditado.getNombre() != null)
            producto.setNombre(productoEditado.getNombre());
        if (productoEditado.getMarca()!= null)
            producto.setMarca(productoEditado.getMarca());
        if (productoEditado.getCosto()!= null)
            producto.setCosto(productoEditado.getCosto());
        if (productoEditado.getCantidadDisponible()!= null)
            producto.setCantidadDisponible(productoEditado.getCantidadDisponible());
        
        return productoRepository.save(producto);
    }

    @Override
    public List<Product> getLowStockProducts() {
        return productoRepository.findByCantidadDisponibleLessThan(5.0);
    }
    
}

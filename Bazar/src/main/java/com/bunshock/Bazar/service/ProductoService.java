package com.bunshock.Bazar.service;

import com.bunshock.Bazar.dto.ProductoDTO;
import com.bunshock.Bazar.model.Producto;
import com.bunshock.Bazar.repository.IProductoRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ProductoService implements IProductoService {
    
    private final IProductoRepository productoRepository;

    @Autowired
    public ProductoService(IProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    @Override
    public void saveCliente(ProductoDTO datosProducto) {
        Producto producto = new Producto();
        
        producto.setNombre(datosProducto.getNombre());
        producto.setMarca(datosProducto.getMarca());
        producto.setCosto(datosProducto.getCosto());
        producto.setCantidadDisponible(datosProducto.getCantidadDisponible());
        
        productoRepository.save(producto);
    }

    @Override
    public List<Producto> getProductos() {
        return productoRepository.findAll();
    }

    @Override
    public Producto getProductoById(Long id) {
        return productoRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteProducto(Long id) {
        productoRepository.deleteById(id);
    }

    @Override
    public Producto editProducto(Long id, ProductoDTO productoEditado) {
        Producto producto = this.getProductoById(id);
        
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
    public List<Producto> getLowStockProducts() {
        return productoRepository.findByCantidadDisponibleLessThan(5.0);
    }
    
}

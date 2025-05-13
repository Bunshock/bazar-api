package com.bunshock.Bazar.service;

import com.bunshock.Bazar.dto.VentaDTO;
import com.bunshock.Bazar.model.Cliente;
import com.bunshock.Bazar.model.Producto;
import com.bunshock.Bazar.model.Venta;
import com.bunshock.Bazar.repository.IClienteRepository;
import com.bunshock.Bazar.repository.IProductoRepository;
import com.bunshock.Bazar.repository.IVentaRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class VentaService implements IVentaService {
    
    private final IVentaRepository ventaRepository;
    private final IProductoRepository productoRepository;
    private final IClienteRepository clienteRepository;
    
    @Autowired
    public VentaService(IVentaRepository ventaRepository,
            IProductoRepository productoRepository, IClienteRepository clienteRepository) {
        this.ventaRepository = ventaRepository;
        this.productoRepository = productoRepository;
        this.clienteRepository = clienteRepository;
    }

    @Override
    @Transactional
    public void saveVenta(VentaDTO datosVenta) {
        Venta venta = new Venta();
        
        venta.setFecha_venta(datosVenta.getFecha_venta());
        venta.setTotal(datosVenta.getTotal());
        
        // Obtengo todos los productos con los ids ingresados. Si alguno
        // de los ids ingresados no se corresponde con ningun producto existente
        // en la base de datos, simplemente lo ignoro
        List<Producto> listaProductos = productoRepository
                .findAllById(datosVenta.getListaIdProductos());
        venta.setListaProductos(listaProductos);
        
        // Obtengo el Cliente relacionado al id ingresado
        Cliente cliente = clienteRepository.findById(datosVenta.getIdCliente())
                .orElseThrow(() -> new EntityNotFoundException("No se encontró"
                        + " cliente con id: " + datosVenta.getIdCliente()));
        venta.setUnCliente(cliente);
        
        ventaRepository.save(venta);
    }

    @Override
    public List<Venta> getVentas() {
        return ventaRepository.findAll();
    }

    @Override
    public Venta getVentaById(Long id) {
        return ventaRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteVenta(Long id) {
        ventaRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Venta editVenta(Long id, VentaDTO ventaEditada) {
        Venta venta = this.getVentaById(id);
        
        if (ventaEditada.getFecha_venta() != null)
            venta.setFecha_venta(ventaEditada.getFecha_venta());
        if (ventaEditada.getTotal() != null)
            venta.setTotal(ventaEditada.getTotal());
        if (ventaEditada.getListaIdProductos() != null) {
            List<Producto> listaProductos = productoRepository
                .findAllById(ventaEditada.getListaIdProductos());
            venta.setListaProductos(listaProductos);
        }
        if (ventaEditada.getIdCliente() != null) {
            Cliente cliente = clienteRepository.findById(ventaEditada.getIdCliente())
                .orElseThrow(() -> new EntityNotFoundException("No se encontró"
                        + " cliente con id: " + ventaEditada.getIdCliente()));
            venta.setUnCliente(cliente);
        }
        
        return ventaRepository.save(venta);
    }

    @Override
    public List<Producto> getVentaProductos(Long id) {
        return this.getVentaById(id).getListaProductos();
    }
    
}

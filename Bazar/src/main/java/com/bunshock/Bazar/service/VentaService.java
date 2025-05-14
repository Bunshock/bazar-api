package com.bunshock.Bazar.service;

import com.bunshock.Bazar.dto.ResumenVentasDTO;
import com.bunshock.Bazar.dto.VentaDTO;
import com.bunshock.Bazar.model.Cliente;
import com.bunshock.Bazar.model.Producto;
import com.bunshock.Bazar.model.Venta;
import com.bunshock.Bazar.repository.IClienteRepository;
import com.bunshock.Bazar.repository.IProductoRepository;
import com.bunshock.Bazar.repository.IVentaRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
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
        
        // Para descontar los productos vendidos de cantidad_disponible en Producto
        // (siempre y cuando la cantidad vendida no supere la cantidad disponible)
        // junto los ids de los productos, con las veces que aparecen en la lista
        // de ids de productos recibida.
        Map<Long, Long> cantidadesPorId = datosVenta.getListaIdProductos().stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        // Obtengo todos los productos con los ids ingresados. Si un producto
        // ingresado no existe, tiramos excepción. Además, verifico que la cantidad
        // de dicho producto que se quiere vender no supera la cantidad disponible.
        // Dado el caso que se supere la cantidad disponible, tiro una excepcion.
        List<Producto> listaProductos = cantidadesPorId.entrySet().stream()
                .map(entry -> {
                    Long id = entry.getKey();
                    Long cantidad = entry.getValue();
                    
                    Producto producto = productoRepository.findById(id)
                            .orElseThrow(() -> new EntityNotFoundException("No se"
                                    + " encontró producto con id: " + id));
                    
                    if (producto.getCantidadDisponible() < cantidad)
                        throw new IllegalArgumentException("Stock insuficiente"
                                + " para el producto (" + producto.getNombre()
                                + ") con id: " + id);
                    
                    producto.setCantidadDisponible(producto.getCantidadDisponible() - cantidad);
                    return producto;
                })
                .collect(Collectors.toList());
        
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

    @Override
    public ResumenVentasDTO getVentaResumeByDate(LocalDate fecha) { 
        Object[] resumen = ventaRepository.getResumeByDate(fecha).get(0);
        
        Double montoDouble = (Double) resumen[0];
        Long cantidadLong = (Long) resumen[1];
        
        // SUM puede devolver null si no hay coincidencias.
        Double montoTotal = montoDouble != null ? montoDouble : 0.0;
        // COUNT devuelve 0 si no hay coincidencias, no chequeamos por null.
        int cantidadVentas = cantidadLong.intValue();
        
        return new ResumenVentasDTO(montoTotal, cantidadVentas);
    }

    @Override
    public Venta getHighestTotalVenta() {
        return ventaRepository.findTopByOrderByTotalDesc();
    }
    
}

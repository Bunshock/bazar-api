package com.bunshock.Bazar.service;

import com.bunshock.Bazar.dto.ResumenVentasDTO;
import com.bunshock.Bazar.dto.VentaDTO;
import com.bunshock.Bazar.dto.VentaMostrarDTO;
import com.bunshock.Bazar.model.Cliente;
import com.bunshock.Bazar.model.Producto;
import com.bunshock.Bazar.model.UserEntity;
import com.bunshock.Bazar.model.Venta;
import com.bunshock.Bazar.repository.IClienteRepository;
import com.bunshock.Bazar.repository.IProductoRepository;
import com.bunshock.Bazar.repository.IUserRepository;
import com.bunshock.Bazar.repository.IVentaRepository;
import com.bunshock.Bazar.utils.IServiceUtils;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class VentaService implements IVentaService {
    
    private final IVentaRepository ventaRepository;
    private final IProductoRepository productoRepository;
    private final IClienteRepository clienteRepository;
    private final IUserRepository userRepository;
    private final IServiceUtils serviceUtils;
    
    @Autowired
    public VentaService(IVentaRepository ventaRepository,
            IProductoRepository productoRepository, IClienteRepository clienteRepository,
            IUserRepository userRepository, IServiceUtils serviceUtils) {
        this.ventaRepository = ventaRepository;
        this.productoRepository = productoRepository;
        this.clienteRepository = clienteRepository;
        this.userRepository = userRepository;
        this.serviceUtils = serviceUtils;
    }

    @Override
    @Transactional
    public void saveVenta(VentaDTO datosVenta, Long id_cliente) {
        Venta venta = new Venta();
        
        venta.setFecha_venta(datosVenta.getFecha_venta());
        venta.setTotal(datosVenta.getTotal());

        // Obtengo todos los productos con los ids ingresados. Si un producto
        // ingresado no existe, tiramos excepción.
        List<Producto> listaProductos = datosVenta.getListaIdProductos().stream()
                .map(id -> productoRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("No se"
                                    + " encontró producto con id: " + id))
                )
                .collect(Collectors.toList());
        venta.setListaProductos(listaProductos);
        
        // Obtengo el Cliente relacionado al id ingresado
        Cliente cliente = clienteRepository.findById(id_cliente)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró"
                        + " cliente con id: " + id_cliente));
        venta.setUnCliente(cliente);
        
        ventaRepository.save(venta);
    }

    @Override
    public List<VentaMostrarDTO> getVentas() {
        List<Venta> listaVentas = ventaRepository.findAll();
        
        return listaVentas.stream()
                .map(venta -> serviceUtils.mapVentaToVentaMostrarDTO(venta))
                .collect(Collectors.toList());
    }

    @Override
    public VentaMostrarDTO getVentaById(Long codigo_venta) {
        Venta venta = ventaRepository.findById(codigo_venta)
                .orElseThrow(() -> new EntityNotFoundException("La venta con "
                        + "id (" + codigo_venta + ") no fue encontrada"));
        
        return serviceUtils.mapVentaToVentaMostrarDTO(venta);
    }

    @Override
    public void deleteVenta(Long codigo_venta) {
        ventaRepository.deleteById(codigo_venta);
    }

    @Override
    @Transactional
    public VentaMostrarDTO editVenta(Long codigo_venta, VentaDTO ventaEditada, Long id_cliente) {
        Venta venta = ventaRepository.findById(codigo_venta)
                .orElseThrow(() -> new EntityNotFoundException("La venta con "
                        + "id (" + codigo_venta + ") no fue encontrada"));
        
        if (ventaEditada.getFecha_venta() != null)
            venta.setFecha_venta(ventaEditada.getFecha_venta());
        if (ventaEditada.getTotal() != null)
            venta.setTotal(ventaEditada.getTotal());
        if (ventaEditada.getListaIdProductos() != null) {
            List<Producto> listaProductos = productoRepository
                .findAllById(ventaEditada.getListaIdProductos());
            venta.setListaProductos(listaProductos);
        }
        if (id_cliente != null) {
            Cliente cliente = clienteRepository.findById(id_cliente)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró"
                        + " cliente con id: " + id_cliente));
            venta.setUnCliente(cliente);
        }
        
        ventaRepository.save(venta);
        
        return serviceUtils.mapVentaToVentaMostrarDTO(venta);
    }

    @Override
    public List<Producto> getVentaProductos(Long codigo_venta) {
        Venta venta = ventaRepository.findById(codigo_venta)
                .orElseThrow(() -> new EntityNotFoundException("La venta con "
                        + "id (" + codigo_venta + ") no fue encontrada"));
        return venta.getListaProductos();
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

    @Override
    @Transactional
    public void concretarVenta(Long codigo_venta) {
        Venta venta = ventaRepository.findById(codigo_venta)
                .orElseThrow(() -> new EntityNotFoundException("La venta con "
                        + "id (" + codigo_venta + ") no fue encontrada"));

        // Para descontar los productos vendidos de cantidad_disponible en Producto
        // (siempre y cuando la cantidad vendida no supere la cantidad disponible)
        // junto los ids de los productos, con las veces que aparecen en la lista
        // de ids de productos recibida.
        Map<Long, Long> cantidadesPorId = venta.getListaProductos().stream()
                .map(producto -> producto.getCodigo_producto())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        
        // Verifico que la cantidad de dicho producto que se quiere vender no supera
        // la cantidad disponible. Dado el caso que se supere la cantidad disponible,
        // tiro una excepcion.
        cantidadesPorId.entrySet().stream()
                .map(entry -> {
                    Long codigoProducto = entry.getKey();
                    Long cantidadDisponible = entry.getValue();
                    
                    Producto producto = productoRepository.findById(codigoProducto)
                            .orElseThrow(() -> new EntityNotFoundException("No se"
                                    + " encontró producto con código: " + codigoProducto));
                    
                    if (producto.getCantidadDisponible() < cantidadDisponible)
                        throw new IllegalArgumentException("Stock insuficiente"
                                + " para el producto (" + producto.getNombre()
                                + ") con código: " + codigoProducto);
                    
                    producto.setCantidadDisponible(producto.getCantidadDisponible() - cantidadDisponible);
                    productoRepository.save(producto);
                    return producto;
                });
        
        venta.setRealizada(true);
        
        ventaRepository.save(venta);
    }
    
    @Override
    @Transactional
    public void saveMiVenta(VentaDTO datosVenta) {
        // Obtenemos el usuario relacionado al cliente logueado
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("El usuario con "
                        + "username (" + username + ") no existe"));
        
        Venta venta = new Venta();
        
        venta.setUnCliente(user.getCliente());
        
        venta.setFecha_venta(datosVenta.getFecha_venta());
        venta.setTotal(datosVenta.getTotal());

        // Obtengo todos los productos con los ids ingresados. Si un producto
        // ingresado no existe, tiramos excepción.
        List<Producto> listaProductos = datosVenta.getListaIdProductos().stream()
                .map(id -> productoRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("No se"
                                    + " encontró producto con id: " + id))
                )
                .collect(Collectors.toList());
        venta.setListaProductos(listaProductos);
        
        ventaRepository.save(venta);
    }
    
    @Override
    public List<VentaMostrarDTO> getMisVentas() {
        // Obtenemos el usuario relacionado al cliente logueado
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("El usuario con "
                        + "username (" + username + ") no existe"));
        
        List<Venta> listaMisVentas = ventaRepository.findByUnCliente_IdCliente(
                user.getCliente().getIdCliente());
        
        return listaMisVentas.stream()
                .map(venta -> serviceUtils.mapVentaToVentaMostrarDTO(venta))
                .collect(Collectors.toList());
    }

    @Override
    public VentaMostrarDTO getMiVentaById(Long codigo_venta) {
        // Obtenemos el usuario relacionado al cliente logueado
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("El usuario con "
                        + "username (" + username + ") no existe"));
        
        Venta venta = ventaRepository.findByUnCliente_IdClienteAndCodigoVenta(
                user.getCliente().getIdCliente(), codigo_venta).orElseThrow(
                        () -> new EntityNotFoundException("El cliente con id (" 
                                + user.getCliente().getIdCliente() + ") no tiene asociado "
                                        + "una venta con código: " + codigo_venta)
                );
        
        return serviceUtils.mapVentaToVentaMostrarDTO(venta);
    }

    @Override
    public void deleteMiVenta(Long codigo_venta) {
        // Obtenemos el usuario logueado
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("El usuario con "
                        + "username (" + username + ") no existe"));
        
        // Verificamos que la venta indicada corresponda al cliente del usuario logueado
        Venta venta = ventaRepository.findByUnCliente_IdClienteAndCodigoVenta(
                user.getCliente().getIdCliente(), codigo_venta).orElseThrow(
                        () -> new EntityNotFoundException("El cliente con id (" 
                                + user.getCliente().getIdCliente() + ") no tiene asociado "
                                        + "una venta con código: " + codigo_venta)
                );
        
        ventaRepository.delete(venta);
    }
    
}

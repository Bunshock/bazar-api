package com.bunshock.Bazar.utils;

import com.bunshock.Bazar.dto.ProductoVentaDTO;
import com.bunshock.Bazar.dto.VentaMostrarDTO;
import com.bunshock.Bazar.model.Venta;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;


@Component
public class ServiceUtils implements IServiceUtils {

    @Override
    public VentaMostrarDTO mapVentaToVentaMostrarDTO(Venta venta) {
        
        // Para mostrar la cantidad de cada producto, necesitamos contar
        Map<Long, Long> cantidadesPorId = venta.getListaProductos().stream()
                .map(producto -> producto.getCodigo_producto())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        
        return new VentaMostrarDTO(
                venta.getFecha_venta(),
                venta.getTotal(),
                venta.isRealizada(),
                venta.getListaProductos().stream()
                        .map(producto -> new ProductoVentaDTO(
                                producto.getNombre(),
                                producto.getMarca(),
                                producto.getCosto(),
                                (Double) cantidadesPorId.get(producto.getCodigo_producto())
                                        .doubleValue()
                        ))
                        .collect(Collectors.toList()),
                venta.getUnCliente().getNombre()
        );
    }
    
}

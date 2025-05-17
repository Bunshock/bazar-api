package com.bunshock.Bazar.utils.mapper;

import com.bunshock.Bazar.dto.product.SaleProductDTO;
import com.bunshock.Bazar.dto.sale.ShowSaleDTO;
import com.bunshock.Bazar.model.Product;
import com.bunshock.Bazar.model.Sale;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;


@Component
public class SaleMapper {

    public ShowSaleDTO SaleToShowSaleDTO(Sale venta) {
        
        // Como muestro la cantidad de cada producto comprado, elimino duplicados
        Set<Product> productosUnicos = new HashSet<>(venta.getListaProductos());

        // Para mostrar la cantidad de cada producto, necesito contar
        Map<Long, Long> cantidadesPorId = venta.getListaProductos().stream()
                .map(producto -> producto.getCodigo_producto())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        
        return new ShowSaleDTO(
                venta.getCodigoVenta(),
                venta.getFecha_venta(),
                venta.getTotal(),
                venta.isRealizada(),
                productosUnicos.stream()
                        .map(producto -> new SaleProductDTO(
                                producto.getNombre(),
                                producto.getMarca(),
                                producto.getCosto(),
                                (Double) cantidadesPorId.get(producto.getCodigo_producto())
                                        .doubleValue()
                        ))
                        .collect(Collectors.toList()),
                venta.getUnCliente().getDni()
        );
    }
    
}

package com.bunshock.Bazar.utils.mapper;

import com.bunshock.Bazar.dto.product.SaleProductDTO;
import com.bunshock.Bazar.dto.sale.HighestSaleDTO;
import com.bunshock.Bazar.dto.sale.ShowSaleDTO;
import com.bunshock.Bazar.model.Product;
import com.bunshock.Bazar.model.Sale;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;


public class SaleMapper {

    public static ShowSaleDTO SaleToShowSaleDTO(Sale sale) {
        
        // Como muestro la cantidad de cada producto comprado, elimino duplicados
        Set<Product> productosUnicos = new HashSet<>(sale.getProductList());

        // Para mostrar la cantidad de cada producto, necesito contar
        Map<Long, Long> cantidadesPorId = sale.getProductList().stream()
                .map(producto -> producto.getProductCode())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        
        return new ShowSaleDTO(
                sale.getSaleCode(),
                sale.getSaleDate(),
                sale.getTotalPrice(),
                sale.isFinalized(),
                productosUnicos.stream()
                        .map(producto -> new SaleProductDTO(
                                producto.getName(),
                                producto.getBrand(),
                                producto.getPrice(),
                                (Double) cantidadesPorId.get(producto.getProductCode())
                                        .doubleValue()
                        ))
                        .collect(Collectors.toList()),
                sale.getClient().getIdClient()
        );
    }
    
    public static HighestSaleDTO SaleToHighestSaleDTO(Sale sale) {
        return new HighestSaleDTO(
                sale.getSaleCode(),
                sale.getTotalPrice(),
                sale.getProductList().size(),
                sale.getClient().getFirstName(),
                sale.getClient().getLastName()
        );
    }
    
}

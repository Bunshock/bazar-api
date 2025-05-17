package com.bunshock.Bazar.dto.sale;

import com.bunshock.Bazar.dto.product.SaleProductDTO;
import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class ShowSaleDTO {
    
    private Long codigo_venta;
    private LocalDate fecha_venta;
    private Double total;
    private boolean realizada;
    
    private List<SaleProductDTO> productosComprados;
    
    private String dni_cliente;

    public ShowSaleDTO() {
    }

    public ShowSaleDTO(Long codigo_venta, LocalDate fecha_venta, Double total,
            boolean realizada, List<SaleProductDTO> productosComprados, String nombre_cliente) {
        this.codigo_venta = codigo_venta;
        this.fecha_venta = fecha_venta;
        this.total = total;
        this.realizada = realizada;
        this.productosComprados = productosComprados;
        this.dni_cliente = nombre_cliente;
    }

    
    
}

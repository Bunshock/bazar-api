package com.bunshock.Bazar.dto;

import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class VentaMostrarDTO {
    
    private Long codigo_venta;
    private LocalDate fecha_venta;
    private Double total;
    private boolean realizada;
    
    private List<ProductoVentaDTO> productosComprados;
    
    private String dni_cliente;

    public VentaMostrarDTO() {
    }

    public VentaMostrarDTO(Long codigo_venta, LocalDate fecha_venta, Double total,
            boolean realizada, List<ProductoVentaDTO> productosComprados, String nombre_cliente) {
        this.codigo_venta = codigo_venta;
        this.fecha_venta = fecha_venta;
        this.total = total;
        this.realizada = realizada;
        this.productosComprados = productosComprados;
        this.dni_cliente = nombre_cliente;
    }

    
    
}

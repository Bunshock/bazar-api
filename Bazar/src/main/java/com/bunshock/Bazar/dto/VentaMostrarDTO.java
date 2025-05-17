package com.bunshock.Bazar.dto;

import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class VentaMostrarDTO {
    
    private LocalDate fecha_venta;
    private Double total;
    private boolean realizada;
    
    private List<ProductoVentaDTO> productosComprados;
    
    private String nombre_cliente;

    public VentaMostrarDTO() {
    }

    public VentaMostrarDTO(LocalDate fecha_venta, Double total, boolean realizada,
            List<ProductoVentaDTO> productosComprados, String nombre_cliente) {
        this.fecha_venta = fecha_venta;
        this.total = total;
        this.realizada = realizada;
        this.productosComprados = productosComprados;
        this.nombre_cliente = nombre_cliente;
    }
    
}

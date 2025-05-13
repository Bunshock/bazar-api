package com.bunshock.Bazar.dto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class ResumenVentasDTO {
    
    private Double montoTotal;
    private int cantidadVentas;

    public ResumenVentasDTO() {
    }

    public ResumenVentasDTO(Double montoTotal, int cantidadVentas) {
        this.montoTotal = montoTotal;
        this.cantidadVentas = cantidadVentas;
    }    
    
}

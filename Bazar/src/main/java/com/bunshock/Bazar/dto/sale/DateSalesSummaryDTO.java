package com.bunshock.Bazar.dto.sale;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class DateSalesSummaryDTO {
    
    private Double montoTotal;
    private int cantidadVentas;

    public DateSalesSummaryDTO() {
    }

    public DateSalesSummaryDTO(Double montoTotal, int cantidadVentas) {
        this.montoTotal = montoTotal;
        this.cantidadVentas = cantidadVentas;
    }    
    
}

package com.bunshock.Bazar.dto.product;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class SaleProductDTO {
    
    private String name;
    private String marca;
    private Double costo;
    private Double cantidadComprada;

    public SaleProductDTO() {
    }

    public SaleProductDTO(String name, String marca, Double costo, Double cantidadComprada) {
        this.name = name;
        this.marca = marca;
        this.costo = costo;
        this.cantidadComprada = cantidadComprada;
    }
    
}

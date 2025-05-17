package com.bunshock.Bazar.dto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class ProductoVentaDTO {
    
    private String name;
    private String marca;
    private Double costo;
    private Double cantidadComprada;

    public ProductoVentaDTO() {
    }

    public ProductoVentaDTO(String name, String marca, Double costo, Double cantidadComprada) {
        this.name = name;
        this.marca = marca;
        this.costo = costo;
        this.cantidadComprada = cantidadComprada;
    }
    
}

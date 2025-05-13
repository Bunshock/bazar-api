package com.bunshock.Bazar.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class ProductoDTO {
    
    @NotBlank(message = "El nombre no puede estar vacío", groups = OnCreate.class)
    @Size(min = 2, max = 50, message = "El nombre debe tener una longitud"
            + " entre 2 y 50 caracteres", groups = {OnCreate.class, OnUpdate.class})
    private String nombre;
    
    @NotBlank(message = "La marca no puede estar vacía", groups = OnCreate.class)
    @Size(min = 1, max = 50, message = "La marca debe tener una longitud"
            + " entre 1 y 50 caracteres", groups = {OnCreate.class, OnUpdate.class})
    private String marca;
    
    @NotNull(message = "El costo no puede ser nulo", groups = OnCreate.class)
    @Positive(message = "El costo debe ser un valor numérico positivo",
            groups = {OnCreate.class, OnUpdate.class})
    private Double costo;
    
    @NotNull(message = "La cantidad disponible no puede ser nula", groups = OnCreate.class)
    @PositiveOrZero(message = "La cantidad disponible debe ser un número no negativo",
            groups = {OnCreate.class, OnUpdate.class})
    private Double cantidad_disponible;

    public ProductoDTO() {
    }

    public ProductoDTO(String nombre, String marca, Double costo,
            Double cantidad_disponible) {
        this.nombre = nombre;
        this.marca = marca;
        this.costo = costo;
        this.cantidad_disponible = cantidad_disponible;
    }
    
}

package com.bunshock.Bazar.dto.product;

import com.bunshock.Bazar.dto.OnCreate;
import com.bunshock.Bazar.dto.OnUpdate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class ProductDTO {
    
    @NotBlank(message = "El nombre no puede estar vacío", groups = OnCreate.class)
    @Size(min = 2, max = 50, message = "El nombre debe tener una longitud"
            + " entre 2 y 50 caracteres", groups = {OnCreate.class, OnUpdate.class})
    private String name;
    
    @NotBlank(message = "La marca no puede estar vacía", groups = OnCreate.class)
    @Size(min = 1, max = 50, message = "La marca debe tener una longitud"
            + " entre 1 y 50 caracteres", groups = {OnCreate.class, OnUpdate.class})
    private String brand;
    
    @NotNull(message = "El costo no puede ser nulo", groups = OnCreate.class)
    @Positive(message = "El costo debe ser un valor numérico positivo",
            groups = {OnCreate.class, OnUpdate.class})
    private Double price;
    
    @NotNull(message = "La cantidad disponible no puede ser nula", groups = OnCreate.class)
    @PositiveOrZero(message = "La cantidad disponible debe ser un número no negativo",
            groups = {OnCreate.class, OnUpdate.class})
    private Double amountAvailable;

    public ProductDTO() {
    }

    public ProductDTO(String name, String brand, Double price,
            Double amountAvailable) {
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.amountAvailable = amountAvailable;
    }
    
}

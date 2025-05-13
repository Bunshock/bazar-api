package com.bunshock.Bazar.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class ClienteDTO {
    
    @NotBlank(message = "El nombre no puede estar vacío", groups = OnCreate.class)
    @Size(min = 2, max = 50, message = "El nombre debe tener una longitud"
            + " entre 2 y 50 caracteres", groups = {OnCreate.class, OnUpdate.class})
    private String nombre;
    
    @NotBlank(message = "El apellido no puede estar vacío", groups = OnCreate.class)
    @Size(min = 2, max = 50, message = "El apellido debe tener una longitud"
            + " entre 2 y 50 caracteres", groups = {OnCreate.class, OnUpdate.class})
    private String apellido;
    
    @NotBlank(message = "El dni no puede estar vacío", groups = OnCreate.class)
    @Pattern(regexp = "^(\\d{1,2})\\.(\\d{3})\\.(\\d{3})$", message = "Formato"
            + " de DNI inválido", groups = {OnCreate.class, OnUpdate.class})
    private String dni;

    public ClienteDTO() {
    }

    public ClienteDTO(String nombre, String apellido, String dni) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
    }
    
}

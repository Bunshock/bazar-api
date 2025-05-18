package com.bunshock.Bazar.dto.client;

import com.bunshock.Bazar.dto.OnCreate;
import com.bunshock.Bazar.dto.OnUpdate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class InputClientDTO {
    
    @NotBlank(message = "El nombre no puede estar vacío", groups = OnCreate.class)
    @Size(min = 2, max = 50, message = "El nombre debe tener una longitud"
            + " entre 2 y 50 caracteres", groups = {OnCreate.class, OnUpdate.class})
    private String firstName;
    
    @NotBlank(message = "El apellido no puede estar vacío", groups = OnCreate.class)
    @Size(min = 2, max = 50, message = "El apellido debe tener una longitud"
            + " entre 2 y 50 caracteres", groups = {OnCreate.class, OnUpdate.class})
    private String lastName;
    
    @NotBlank(message = "El dni no puede estar vacío", groups = OnCreate.class)
    @Pattern(regexp = "^(\\d{1,2})\\.(\\d{3})\\.(\\d{3})$", message = "Formato"
            + " de DNI inválido", groups = {OnCreate.class, OnUpdate.class})
    private String dni;

    public InputClientDTO() {
    }

    public InputClientDTO(String firstName, String lastName, String dni) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dni = dni;
    }
    
}

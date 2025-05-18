package com.bunshock.Bazar.dto.auth;

import com.bunshock.Bazar.dto.OnCreate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class LoginUserDTO {
    
    @NotBlank(message = "El username no puede estar vacío", groups = OnCreate.class)
    @Size(min = 4, max = 30, message = "El username debe tener una longitud"
            + " entre 4 y 30 caracteres", groups = OnCreate.class)
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "El username debe contener"
            + " sólo caracteres alfanuméricos: (a-z) (A-Z) (0-9)", groups = OnCreate.class)
    private String username;
    
    @NotBlank(message = "La contraseña no puede estar vacía", groups = OnCreate.class)
    @Size(min = 8, max = 60, message = "La contraseña debe tener una longitud"
            + " entre 8 y 60 caracteres", groups = OnCreate.class)
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=]).*$",
            message = "La contraseña requiere al menos una letra en mayúscula,"
                    + " una letra en minúscula, un dígito, y un caracter especial",
            groups = OnCreate.class)
    private String password;

    public LoginUserDTO() {
    }

    public LoginUserDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }
    
}

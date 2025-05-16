package com.bunshock.Bazar.dto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class RegisterUserDTO {
    
    private String username;
    private String password;

    public RegisterUserDTO() {
    }

    public RegisterUserDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }
    
}

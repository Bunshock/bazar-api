package com.bunshock.Bazar.dto.auth;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class LoginUserDTO {
    
    private String username;
    private String password;

    public LoginUserDTO() {
    }

    public LoginUserDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }
    
}

package com.bunshock.Bazar.dto.auth;


public class JwtResponseDTO {
    
    private String token;

    public JwtResponseDTO() {
    }

    public JwtResponseDTO(String token) {
        this.token = token;
    }
    
    public String getToken() {
        return this.token;
    }
    
}

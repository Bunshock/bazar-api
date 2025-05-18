package com.bunshock.Bazar.dto.client;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class ShowClientDTO {
    
    private Long clientId;
    private String firstName;
    private String lastName;
    private String dni;
    
    private Long userId;

    public ShowClientDTO() {
    }

    public ShowClientDTO(Long clientId, String firstName, String lastName,
            String dni, Long userId) {
        this.clientId = clientId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dni = dni;
        this.userId = userId;
    }
    
}

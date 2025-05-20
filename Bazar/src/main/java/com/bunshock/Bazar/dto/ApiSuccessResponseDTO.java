package com.bunshock.Bazar.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;


@Getter @Setter
@SuperBuilder
public class ApiSuccessResponseDTO<T> extends ApiResponseDTO {
    
    private String message;
    private T data;

    public ApiSuccessResponseDTO() {
    }

    public ApiSuccessResponseDTO(String message, T data, ApiResponseDTOBuilder<?, ?> b) {
        super(b);
        this.message = message;
        this.data = data;
    }
    
}

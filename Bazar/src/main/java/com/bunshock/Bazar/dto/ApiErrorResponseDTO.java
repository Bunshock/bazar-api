package com.bunshock.Bazar.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;


@Getter @Setter
@SuperBuilder
public class ApiErrorResponseDTO extends ApiResponseDTO {

    private List<String> errors;

    public ApiErrorResponseDTO() {
    }

    public ApiErrorResponseDTO(List<String> errors, ApiResponseDTOBuilder<?, ?> b) {
        super(b);
        this.errors = errors;
    }
    
}

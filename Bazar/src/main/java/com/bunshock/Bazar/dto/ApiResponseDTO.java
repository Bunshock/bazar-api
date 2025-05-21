package com.bunshock.Bazar.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;


@Getter @Setter
@SuperBuilder
public abstract class ApiResponseDTO {
    
    private int status;
    
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime timestamp;
    
    public ApiResponseDTO() {
    }
    
    public ApiResponseDTO(int status, LocalDateTime timestamp) {
        this.status = status;
        this.timestamp = timestamp;
    }
    
}

package com.bunshock.Bazar.exception;

import com.bunshock.Bazar.dto.ApiErrorResponseDTO;
import com.bunshock.Bazar.dto.ApiResponseDTO;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;


public class ValidationHandler {
    
    public static ResponseEntity<ApiResponseDTO> handleValidationErrors(BindingResult bindingResult) {
        List<String> errors = bindingResult.getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.toList());

        return new ResponseEntity<>(ApiErrorResponseDTO.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .timestamp(LocalDateTime.now())
                .errors(errors)
                .build(), HttpStatus.BAD_REQUEST);
    }
    
}

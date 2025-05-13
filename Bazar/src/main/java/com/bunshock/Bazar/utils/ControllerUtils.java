package com.bunshock.Bazar.utils;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;


@Component
public class ControllerUtils implements IControllerUtils {
    
    @Override
    public ResponseEntity handleValidationErrors(BindingResult bindingResult) {

        List<String> errores = bindingResult.getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.toList());

        return ResponseEntity.badRequest().body(errores);
    }
    
}

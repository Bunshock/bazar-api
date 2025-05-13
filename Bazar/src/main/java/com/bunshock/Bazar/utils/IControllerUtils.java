package com.bunshock.Bazar.utils;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;


public interface IControllerUtils {
    
    public ResponseEntity handleValidationErrors(BindingResult bindingResult);
    
}

package com.bunshock.Bazar.exception;

import com.bunshock.Bazar.exception.app.AppBusinessException;
import com.bunshock.Bazar.exception.app.AppNotFoundException;
import com.bunshock.Bazar.exception.security.SecurityBusinessException;
import com.bunshock.Bazar.exception.security.SecurityInternalServerException;
import com.bunshock.Bazar.exception.security.SecurityNotFoundException;
import com.bunshock.Bazar.exception.security.SecurityUnauthorizedException;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(AppNotFoundException.class)
    public ResponseEntity<?> handleAppNotFound(AppNotFoundException e) {
        return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AppBusinessException.class)
    public ResponseEntity<?> handleAppBusiness(AppBusinessException e) {
        return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(SecurityNotFoundException.class)
    public ResponseEntity<?> handleSecurityNotFound(SecurityNotFoundException e) {
        return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(SecurityBusinessException.class)
    public ResponseEntity<?> handleSecurityBusiness(SecurityBusinessException e) {
        return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.CONFLICT);
    }
    
    @ExceptionHandler(SecurityUnauthorizedException.class)
    public ResponseEntity<?> handleSecurityUnauthorized(SecurityUnauthorizedException e) {
        return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.UNAUTHORIZED);
    }
    
    @ExceptionHandler(SecurityInternalServerException.class)
    public ResponseEntity<?> handleSecurityInternalServer(SecurityInternalServerException e) {
        return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
}

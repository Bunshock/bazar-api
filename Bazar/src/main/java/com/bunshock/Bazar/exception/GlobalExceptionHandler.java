package com.bunshock.Bazar.exception;

import com.bunshock.Bazar.dto.ApiErrorResponseDTO;
import com.bunshock.Bazar.exception.app.AppBusinessException;
import com.bunshock.Bazar.exception.app.AppNotFoundException;
import com.bunshock.Bazar.exception.security.SecurityBusinessException;
import com.bunshock.Bazar.exception.security.SecurityInternalServerException;
import com.bunshock.Bazar.exception.security.SecurityNotFoundException;
import com.bunshock.Bazar.exception.security.SecurityUnauthorizedException;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(AppNotFoundException.class)
    public ResponseEntity<ApiErrorResponseDTO> handleAppNotFound(AppNotFoundException e) {
        return new ResponseEntity<>(ApiErrorResponseDTO.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .timestamp(LocalDateTime.now())
                .errors(List.of(e.getMessage()))
                .build(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AppBusinessException.class)
    public ResponseEntity<ApiErrorResponseDTO> handleAppBusiness(AppBusinessException e) {
        return new ResponseEntity<>(ApiErrorResponseDTO.builder()
                .status(HttpStatus.CONFLICT.value())
                .timestamp(LocalDateTime.now())
                .errors(List.of(e.getMessage()))
                .build(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(SecurityNotFoundException.class)
    public ResponseEntity<ApiErrorResponseDTO> handleSecurityNotFound(SecurityNotFoundException e) {
        return new ResponseEntity<>(ApiErrorResponseDTO.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .timestamp(LocalDateTime.now())
                .errors(List.of(e.getMessage()))
                .build(), HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(SecurityBusinessException.class)
    public ResponseEntity<ApiErrorResponseDTO> handleSecurityBusiness(SecurityBusinessException e) {
        return new ResponseEntity<>(ApiErrorResponseDTO.builder()
                .status(HttpStatus.CONFLICT.value())
                .timestamp(LocalDateTime.now())
                .errors(List.of(e.getMessage()))
                .build(), HttpStatus.CONFLICT);
    }
    
    @ExceptionHandler(SecurityUnauthorizedException.class)
    public ResponseEntity<ApiErrorResponseDTO> handleSecurityUnauthorized(SecurityUnauthorizedException e) {
        return new ResponseEntity<>(ApiErrorResponseDTO.builder()
                .status(HttpStatus.UNAUTHORIZED.value())
                .timestamp(LocalDateTime.now())
                .errors(List.of(e.getMessage()))
                .build(), HttpStatus.UNAUTHORIZED);
    }
    
    @ExceptionHandler(SecurityInternalServerException.class)
    public ResponseEntity<ApiErrorResponseDTO> handleSecurityInternalServer(SecurityInternalServerException e) {
        return new ResponseEntity<>(ApiErrorResponseDTO.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .timestamp(LocalDateTime.now())
                .errors(List.of(e.getMessage()))
                .build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
}

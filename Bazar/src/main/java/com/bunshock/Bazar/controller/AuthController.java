package com.bunshock.Bazar.controller;

import com.bunshock.Bazar.dto.OnCreate;
import com.bunshock.Bazar.dto.auth.JwtResponseDTO;
import com.bunshock.Bazar.dto.auth.LoginUserDTO;
import com.bunshock.Bazar.dto.auth.RegisterUserDTO;
import com.bunshock.Bazar.exception.ValidationHandler;
import com.bunshock.Bazar.security.config.JwtProvider;
import com.bunshock.Bazar.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtProvider jwtProvider;
    private final IUserService userService;

    @Autowired
    public AuthController(AuthenticationManager authManager, JwtProvider jwtProvider,
            IUserService userService) {
        this.authManager = authManager;
        this.jwtProvider = jwtProvider;
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@Validated(OnCreate.class) @RequestBody RegisterUserDTO registerDTO,
            BindingResult bindingResult) {
        
        if (bindingResult.hasErrors())
            return ValidationHandler.handleValidationErrors(bindingResult);
        
        userService.registerUser(registerDTO);
        return new ResponseEntity<>("Usuario registrado correctamente", HttpStatus.CREATED);
    }

    // Obtengo credenciales de login, y devuevlo un token al cliente (que deberá
    // usar en futuras requests)
    @PostMapping("/login")
    public ResponseEntity<JwtResponseDTO> login(@Validated(OnCreate.class) @RequestBody LoginUserDTO loginDTO,
            BindingResult bindingResult) {
        
        if (bindingResult.hasErrors())
            return ValidationHandler.handleValidationErrors(bindingResult);
        
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDTO.getUsername(),
                        loginDTO.getPassword()
                )
        );

        String token = jwtProvider.generateToken(auth);
        return new ResponseEntity<>(new JwtResponseDTO(token), HttpStatus.OK);
    }

}

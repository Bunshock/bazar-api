package com.bunshock.Bazar.security.config;

import com.bunshock.Bazar.security.config.JwtProvider;
import com.bunshock.Bazar.security.service.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;


public class JwtAuthFilter extends OncePerRequestFilter {
    
    private final JwtProvider jwtProvider;
    private final CustomUserDetailsService userDetailsService;

    public JwtAuthFilter(JwtProvider jwtProvider, CustomUserDetailsService userDetailsService) {
        this.jwtProvider = jwtProvider;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        
        String jwt = parseJwt(request);
        // Si el token es válido, lo insertamos en el contexto de seguridad (donde se guarda
        // la información del usuario autenticado)
        if (jwt != null && jwtProvider.validateJwtToken(jwt)) {
            
            // Obtengo UserDetails del usuario portador del token
            String username = jwtProvider.getUsernameFromJwt(jwt);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            
            // Usando las authorities del usuario, creo la autenticación
            UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities()
                    );
            // Seteo la autenticación en el contexto de seguridad
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        // Si el token no es válido, no se crea la entrada en el contexto de
        // seguridad y el usuario no podrá realizar operaciones que requieran
        // autenticación.
        
        // Pasamos al siguiente filtro en la cadena
        filterChain.doFilter(request, response);
    }
    
    private String parseJwt(HttpServletRequest request) {
        // Obtengo y devuelvo el token del header de la request
        String headerAuth = request.getHeader("Authorization");
        if (headerAuth != null && headerAuth.startsWith("Bearer "))
            return headerAuth.substring(7);
        // Si no hay token en el header, devuelvo null
        return null;
    }
    
}

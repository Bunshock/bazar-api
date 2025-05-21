package com.bunshock.Bazar.security.config;

import com.bunshock.Bazar.exception.security.JwtGetUsernameException;
import com.bunshock.Bazar.exception.security.JwtInvalidTokenException;
import com.bunshock.Bazar.security.service.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.filter.OncePerRequestFilter;


public class JwtAuthFilter extends OncePerRequestFilter {
    
    private final JwtProvider jwtProvider;
    private final CustomUserDetailsService userDetailsService;
    private final AuthenticationEntryPoint authenticationEntryPoint;

    public JwtAuthFilter(JwtProvider jwtProvider, CustomUserDetailsService userDetailsService,
            AuthenticationEntryPoint authenticationEntryPoint) {
        this.jwtProvider = jwtProvider;
        this.userDetailsService = userDetailsService;
        this.authenticationEntryPoint = authenticationEntryPoint;
    }
    
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getServletPath();
        
        return (path.equals("/productos") && request.getMethod().equals("GET"))
                || (path.matches("^/productos/\\d+$") && request.getMethod().equals("GET"))
                || path.equals("/auth/login")
                || path.equals("/auth/register")
                || path.equals("/swagger-ui.html")
                || path.startsWith("/swagger-ui/")
                || path.startsWith("/v3/api-docs/");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
            FilterChain filterChain) throws IOException, ServletException {
        
        String jwt = parseJwt(request);
        
        try {
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
            } else {
                // Si el token no es válido, no se crea la entrada en el contexto de
                // seguridad y el usuario no podrá realizar operaciones que requieran
                // autenticación.
                
                // Notar que ahora, cualquier petición sin token será captada y devolverá
                // una excepción. Implemento shouldNotFilter(). Esto simplemente hace que esas
                // rutas no requieran token. Pero se sigue aplicando el filtro de SecurityConfig
                SecurityContextHolder.clearContext();
                authenticationEntryPoint.commence(request, response,
                        new AuthenticationException("Fallo en la validación de token: token nulo o inválido") {});
                return;
            }
            
        } catch(JwtInvalidTokenException | JwtGetUsernameException e) {
            // JWT inválido, malformado, expirado, etc.
            SecurityContextHolder.clearContext();
            authenticationEntryPoint.commence(request, response,
                    new AuthenticationException(e.getMessage()) {});
            return;
        }
        
        // Pasamos al siguiente filtro en la cadena
        try {
            filterChain.doFilter(request, response);
        } catch(ServletException | IOException e) {
            SecurityContextHolder.clearContext();
            authenticationEntryPoint.commence(request, response,
                    new AuthenticationException("Error interno en el filtro de seguridad: " + e.getMessage()) {});
        }
        
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

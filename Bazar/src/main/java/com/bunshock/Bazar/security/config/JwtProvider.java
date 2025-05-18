package com.bunshock.Bazar.security.config;

import com.bunshock.Bazar.exception.security.JwtGenerateTokenException;
import com.bunshock.Bazar.exception.security.JwtGetUsernameException;
import com.bunshock.Bazar.exception.security.JwtInvalidTokenException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.InvalidKeyException;
import io.jsonwebtoken.security.Keys;
import java.util.Base64;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;


@Component
public class JwtProvider {
    
    @Value("${app.jwtSecret}")
    private String jwtSecret;
    
    @Value("${app.jwtExpirationMs}")
    private Long jwtExpirationMs;
    
    private SecretKey getSigningKey() {
        byte[] decodedKey = Base64.getDecoder().decode(jwtSecret);
        return Keys.hmacShaKeyFor(decodedKey);
    }
    
    public String generateToken(Authentication authentication) {
        UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
        
        String token;
        try {
            token = Jwts.builder()
                .setSubject(userPrincipal.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + jwtExpirationMs))
                .signWith(getSigningKey(), SignatureAlgorithm.HS512)
                .compact();
        } catch (InvalidKeyException e) {
            throw new JwtGenerateTokenException(e.getMessage());
        }
        
        return token;
    }
    
    public String getUsernameFromJwt(String token) {
        String username;
        
        try {
            username = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
        } catch (JwtException | IllegalArgumentException e) {
            throw new JwtGetUsernameException(e.getMessage());
        }
        
        return username;
    }
    
    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(authToken);
            return true;
        }
        catch (JwtException | IllegalArgumentException e) {
            throw new JwtInvalidTokenException(e.getMessage());
        }
    }
    
}

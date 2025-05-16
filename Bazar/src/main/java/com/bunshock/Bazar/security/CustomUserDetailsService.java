package com.bunshock.Bazar.security;

import com.bunshock.Bazar.model.RoleEntity;
import com.bunshock.Bazar.model.UserEntity;
import com.bunshock.Bazar.repository.IUserRepository;
import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final IUserRepository userRepository;
    
    @Autowired
    public CustomUserDetailsService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    // Busco los usuarios en la base de datos, tomo los roles y permisos,
    // convirtiendolos a objetos de Spring Security y devuelvo un usuario de Spring Security.
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("El usuario con "
                        + "username (" + username + ") no existe"));
        
        for (RoleEntity role : user.getRoleSet()) {
            System.out.println("Role: " + role.getRoleEnum().name());
        }
        
        Set<GrantedAuthority> authorities = new HashSet<>();
        
        // Agregamos los roles al user (con prefijo ROLE_)
        user.getRoleSet().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleEnum().name()));
            
            // Agregar los permisos del rol
            role.getPermissionSet().forEach(permission -> 
                authorities.add(new SimpleGrantedAuthority(permission.getName()))
            );
        });
        
        return new User(
                user.getUsername(),
                user.getPassword(),
                user.isEnabled(),
                user.isAccountNonExpired(),
                user.isCredentialsNonExpired(),
                user.isAccountNonLocked(),
                authorities
        );
    }
    
}

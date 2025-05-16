package com.bunshock.Bazar.security;

import com.bunshock.Bazar.model.UserEntity;
import com.bunshock.Bazar.repository.IUserRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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
        
        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
        
        // Agregamos los roles al user
        user.getRoleSet()
                .forEach(role -> authorityList.add(new SimpleGrantedAuthority(
                        "ROLE_".concat(role.getRoleEnum().name()))));
        
        user.getRoleSet().stream()
                .flatMap(role -> role.getPermissionSet().stream())
                .forEach(permission -> authorityList.add(new SimpleGrantedAuthority(
                        permission.getName())));
        
        return new User(
                user.getUsername(),
                user.getPassword(),
                user.isEnabled(),
                user.isAccountNonExpired(),
                user.isCredentialsNonExpired(),
                user.isAccountNonLocked(),
                authorityList
        );
    }
    
}

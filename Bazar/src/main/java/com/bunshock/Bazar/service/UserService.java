package com.bunshock.Bazar.service;

import com.bunshock.Bazar.dto.RegisterUserDTO;
import com.bunshock.Bazar.model.RoleEntity;
import com.bunshock.Bazar.model.RoleEnum;
import com.bunshock.Bazar.model.UserEntity;
import com.bunshock.Bazar.repository.IRoleRepository;
import com.bunshock.Bazar.repository.IUserRepository;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserService implements IUserService {

    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(IUserRepository userRepository, IRoleRepository roleRepository,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
    @Override
    public void registrarUsuario(RegisterUserDTO registroDTO) {
        
        // Verificamos que no exista otro usuario con el mismo username
        if (userRepository.findByUsername(registroDTO.getUsername()).isPresent())
            throw new RuntimeException("El usuario ya existe");
        
        // Verificamos que el rol 'USER' este correctamente almacenado
        RoleEntity userRole = roleRepository.findByRoleEnum(RoleEnum.USER)
                .orElseThrow(() -> new RuntimeException("El rol 'USER' no fue encontrado"));
        
        // Creamos y guardamos el nuevo usuario
        UserEntity user = UserEntity.builder()
                .username(registroDTO.getUsername())
                .password(passwordEncoder.encode(registroDTO.getPassword()))
                .isEnabled(true)
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .roleSet(Set.of(userRole))
                .build();
        
        userRepository.save(user);
    }
    
}

package com.bunshock.Bazar.service.impl;

import com.bunshock.Bazar.service.interfaces.IUserService;
import com.bunshock.Bazar.dto.auth.RegisterUserDTO;
import com.bunshock.Bazar.exception.security.RegisterDuplicateUsernameException;
import com.bunshock.Bazar.exception.security.RoleNotFoundException;
import com.bunshock.Bazar.model.Client;
import com.bunshock.Bazar.model.RoleEntity;
import com.bunshock.Bazar.model.RoleEnum;
import com.bunshock.Bazar.model.UserEntity;
import com.bunshock.Bazar.repository.IRoleRepository;
import com.bunshock.Bazar.repository.IUserRepository;
import jakarta.transaction.Transactional;
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
    @Transactional
    public void registerUser(RegisterUserDTO inputUser) {
        
        // Verificamos que no exista otro usuario con el mismo username
        if (userRepository.findByUsername(inputUser.getUsername()).isPresent())
            throw new RegisterDuplicateUsernameException(inputUser.getUsername());
        
        // Verificamos que el rol 'USER' este correctamente almacenado
        RoleEntity userRole = roleRepository.findByRoleEnum(RoleEnum.USER)
                .orElseThrow(() -> new RoleNotFoundException("registrar usuario", "USER"));
        
        Client client = new Client();
        
        // Creamos y guardamos el nuevo usuario
        UserEntity user = UserEntity.builder()
                .username(inputUser.getUsername())
                .password(passwordEncoder.encode(inputUser.getPassword()))
                .isEnabled(true)
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .roleSet(Set.of(userRole))
                .client(client)
                .build();
        
        client.setUser(user);
        
        userRepository.save(user);
    }
    
}

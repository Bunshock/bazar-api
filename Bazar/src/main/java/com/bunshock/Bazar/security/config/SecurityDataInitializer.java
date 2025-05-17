package com.bunshock.Bazar.security.config;

import com.bunshock.Bazar.model.PermissionEntity;
import com.bunshock.Bazar.model.RoleEntity;
import com.bunshock.Bazar.model.RoleEnum;
import com.bunshock.Bazar.model.UserEntity;
import com.bunshock.Bazar.repository.IPermissionRepository;
import com.bunshock.Bazar.repository.IRoleRepository;
import com.bunshock.Bazar.repository.IUserRepository;
import jakarta.transaction.Transactional;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
public class SecurityDataInitializer implements CommandLineRunner {

    private final IPermissionRepository permissionRepository;
    private final IRoleRepository roleRepository;
    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public SecurityDataInitializer(IPermissionRepository permissionRepository,
            IRoleRepository roleRepository, IUserRepository userRepository,
            PasswordEncoder passwordEncoder) {
        this.permissionRepository = permissionRepository;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
    @Override
    @Transactional
    public void run(String... args) throws Exception {
        
        // Creo permisos (si no existen en la base de datos)
        PermissionEntity createPermission = permissionRepository.findByName("CREATE")
                .orElseGet(() -> permissionRepository.save(PermissionEntity.builder()
                        .name("CREATE")
                        .build()
                ));
        
        PermissionEntity readPermission = permissionRepository.findByName("READ")
                .orElseGet(() -> permissionRepository.save(PermissionEntity.builder()
                        .name("READ")
                        .build()
                ));
        
        PermissionEntity updatePermission = permissionRepository.findByName("UPDATE")
                .orElseGet(() -> permissionRepository.save(PermissionEntity.builder()
                        .name("UPDATE")
                        .build()
                ));
        
        PermissionEntity deletePermission = permissionRepository.findByName("DELETE")
                .orElseGet(() -> permissionRepository.save(PermissionEntity.builder()
                        .name("DELETE")
                        .build()
                ));
        
        // Creo roles (si no existen en la base de datos)
        RoleEntity adminRole = roleRepository.findByRoleEnum(RoleEnum.ADMIN)
                .orElseGet(() -> roleRepository.save(RoleEntity.builder()
                        .roleEnum(RoleEnum.ADMIN)
                        .permissionSet(Set.of(
                                createPermission,
                                readPermission,
                                updatePermission,
                                deletePermission
                        ))
                        .build()
                ));
        
        roleRepository.findByRoleEnum(RoleEnum.USER)
                .orElseGet(() -> roleRepository.save(RoleEntity.builder()
                        .roleEnum(RoleEnum.USER)
                        .permissionSet(Set.of(
                                createPermission,
                                readPermission
                        ))
                        .build()
                ));
        
        // Creo usuario: admin:admin (si no existe en la base de datos)
        if(userRepository.findByUsername("admin").isEmpty()) {
            UserEntity userAdmin = UserEntity.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("admin"))
                    .isEnabled(true)
                    .accountNonExpired(true)
                    .accountNonLocked(true)
                    .credentialsNonExpired(true)
                    .roleSet(Set.of(adminRole))
                    .build();
        
            userRepository.save(userAdmin);
        }
        
    }
    
}

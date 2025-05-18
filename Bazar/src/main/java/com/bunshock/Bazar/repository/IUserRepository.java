package com.bunshock.Bazar.repository;

import com.bunshock.Bazar.model.UserEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IUserRepository extends JpaRepository<UserEntity, Long> {
    
    // Query Method: buscar usuario por username
    Optional<UserEntity> findByUsername(String username);
    
    // Query Method: verificar si un usuario con cierto username existe
    boolean existsByUsername(String username);
    
}

package com.bunshock.Bazar.repository;

import com.bunshock.Bazar.model.RoleEntity;
import com.bunshock.Bazar.model.RoleEnum;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IRoleRepository extends JpaRepository<RoleEntity, Long> {
    
    // Query Method: buscar rol por nombre
    Optional<RoleEntity> findByRoleEnum(RoleEnum role);
    
}

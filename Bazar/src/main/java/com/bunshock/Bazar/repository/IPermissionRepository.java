package com.bunshock.Bazar.repository;

import com.bunshock.Bazar.model.PermissionEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IPermissionRepository extends JpaRepository<PermissionEntity, Long> {

    // Query Method: buscar permiso por nombre
    Optional<PermissionEntity> findByName(String name);
    
}

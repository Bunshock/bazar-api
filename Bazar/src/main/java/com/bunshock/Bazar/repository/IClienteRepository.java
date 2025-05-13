package com.bunshock.Bazar.repository;

import com.bunshock.Bazar.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IClienteRepository extends JpaRepository<Cliente, Long> {
    
}

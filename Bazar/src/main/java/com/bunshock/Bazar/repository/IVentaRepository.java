package com.bunshock.Bazar.repository;

import com.bunshock.Bazar.model.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IVentaRepository extends JpaRepository<Venta, Long> {
    
}

package com.bunshock.Bazar.repository;

import com.bunshock.Bazar.model.Producto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IProductoRepository extends JpaRepository<Producto, Long> {
    
    List<Producto> findByCantidadDisponibleLessThan(Double cantidad);
    
}

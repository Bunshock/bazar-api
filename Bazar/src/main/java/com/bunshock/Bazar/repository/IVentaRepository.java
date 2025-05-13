package com.bunshock.Bazar.repository;

import com.bunshock.Bazar.model.Venta;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface IVentaRepository extends JpaRepository<Venta, Long> {
    
    // La query devuelve una lista de Object[], donde el primer elemento es un array con
    // los datos que necesito.
    @Query("SELECT SUM(v.total), COUNT(v) FROM Venta v WHERE v.fecha_venta = :fecha")
    List<Object[]> getResumeByDate(LocalDate fecha);
    
}

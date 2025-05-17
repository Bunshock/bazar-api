package com.bunshock.Bazar.repository;

import com.bunshock.Bazar.model.Venta;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface IVentaRepository extends JpaRepository<Venta, Long> {
    
    // Traer la suma de totales y la cantidad de ventas de una determinada fecha.
    // La query devuelve una lista de Object[], donde el primer elemento es un
    // array con los datos que necesito.
    @Query("SELECT SUM(v.total), COUNT(v) FROM Venta v WHERE v.fecha_venta = :fecha")
    List<Object[]> getResumeByDate(LocalDate fecha);
    
    // Traer la venta con mayor monto
    Venta findTopByOrderByTotalDesc();
    // Otra opción sería usar la Query:
    // @Query("SELECT v FROM Venta v WHERE v.total = (SELECT MAX(v2.total) FROM Venta v2)")
    // pero estaría devolviendo todas las ventas con el monto máximo. Yo sólo quiero una
    
    // Traer las ventas de determinado usuario
    List<Venta> findByUnCliente(Long id);
    
}

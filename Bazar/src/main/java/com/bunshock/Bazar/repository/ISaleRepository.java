package com.bunshock.Bazar.repository;

import com.bunshock.Bazar.model.Sale;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface ISaleRepository extends JpaRepository<Sale, Long> {
    
    // Traer la suma de totales y la cantidad de ventas de una determinada fecha.
    // La query devuelve una lista de Object[], donde el primer elemento es un
    // array con los datos que necesito.
    @Query("SELECT SUM(v.total), COUNT(v) FROM Venta v WHERE v.fecha_venta = :fecha")
    List<Object[]> getResumeByDate(LocalDate fecha);
    
    // Traer la venta con mayor monto
    Sale findTopByOrderByTotalDesc();
    // Otra opción sería usar la Query:
    // @Query("SELECT v FROM Venta v WHERE v.total = (SELECT MAX(v2.total) FROM Venta v2)")
    // pero estaría devolviendo todas las ventas con el monto máximo. Yo sólo quiero una
    
    // Traer las ventas de determinado usuario
    List<Sale> findByUnCliente_IdCliente(Long id);
    
    Optional<Sale> findByUnCliente_IdClienteAndCodigoVenta(Long id_cliente, Long codigo_venta);
    
}

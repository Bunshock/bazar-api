package com.bunshock.Bazar.repository;

import com.bunshock.Bazar.model.Sale;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface ISaleRepository extends JpaRepository<Sale, Long> {
    
    // Traer la suma de totales y la cantidad de ventas de una determinada fecha
    @Query("SELECT SUM(s.totalPrice), COUNT(s) FROM Sale s WHERE s.saleDate = :date")
    List<Object[]> getSummaryByDate(@Param("date") LocalDate date);
    
    // Traer la venta con mayor monto
    Sale findTopByOrderByTotalPriceDesc();
    // Otra opción sería usar la Query:
    // @Query("SELECT v FROM Venta v WHERE v.total = (SELECT MAX(v2.total) FROM Venta v2)")
    // pero estaría devolviendo todas las ventas con el monto máximo. Yo sólo quiero una
    
    // Traer todas las ventas de un determinado cliente
    List<Sale> findByClient_IdClient(Long id_client);
    // Traer una venta de un determinado cliente
    Optional<Sale> findByClient_IdClientAndSaleCode(Long id_client, Long sale_code);
    
}

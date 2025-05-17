package com.bunshock.Bazar.service.interfaces;

import com.bunshock.Bazar.dto.sale.DateSalesSummaryDTO;
import com.bunshock.Bazar.dto.sale.InputSaleDTO;
import com.bunshock.Bazar.dto.sale.ShowSaleDTO;
import com.bunshock.Bazar.model.Product;
import com.bunshock.Bazar.model.Sale;
import java.time.LocalDate;
import java.util.List;


public interface ISaleService {
    
    // CRUD Venta
    void saveVenta(InputSaleDTO datosVenta, Long id_cliente);
    List<ShowSaleDTO> getVentas();
    ShowSaleDTO getVentaById(Long codigo_venta);
    void deleteVenta(Long codigo_venta);
    ShowSaleDTO editVenta(Long codigo_venta, InputSaleDTO ventaEditada, Long id_cliente);
    
    List<Product> getVentaProductos(Long codigo_venta);
    DateSalesSummaryDTO getVentaResumeByDate(LocalDate fecha);
    Sale getHighestTotalVenta();
    
    // Descontar productos del stock y finalizar venta. NOTAR: No es necesario
    // tener el stock disponible al momento de crear una venta. Sólo al momento de 
    // concretarla
    void concretarVenta(Long codigo_venta);
    
    void saveMiVenta(InputSaleDTO datosVenta);
    List<ShowSaleDTO> getMisVentas();
    ShowSaleDTO getMiVentaById(Long codigo_venta);
    void deleteMiVenta(Long codigo_venta);
    
}

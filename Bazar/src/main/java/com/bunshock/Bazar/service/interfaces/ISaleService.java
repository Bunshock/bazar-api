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
    void saveSale(InputSaleDTO inputSale, Long id_client);
    List<ShowSaleDTO> getSales();
    ShowSaleDTO getSaleById(Long sale_code);
    void deleteSale(Long sale_code);
    ShowSaleDTO editSale(Long sale_code, InputSaleDTO editedSale, Long id_client);
    
    List<Product> getSaleProducts(Long sale_code);
    DateSalesSummaryDTO getSaleSummaryByDate(LocalDate date);
    Sale getHighestTotalSale();
    
    // Finalizar una venta: descontar productos del stock y finalizar venta.
    // NOTAR: No es necesario tener el stock disponible al momento de crear una venta.
    // Sólo al momento de concretarla
    void finalizeSale(Long sale_code);
    
    void saveMySale(InputSaleDTO inputSale);
    List<ShowSaleDTO> getMySales();
    ShowSaleDTO getMySaleById(Long sale_code);
    void deleteMySale(Long sale_code);
    
}

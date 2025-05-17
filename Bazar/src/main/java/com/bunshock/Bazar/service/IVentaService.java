package com.bunshock.Bazar.service;

import com.bunshock.Bazar.dto.ResumenVentasDTO;
import com.bunshock.Bazar.dto.VentaDTO;
import com.bunshock.Bazar.dto.VentaMostrarDTO;
import com.bunshock.Bazar.model.Producto;
import com.bunshock.Bazar.model.Venta;
import java.time.LocalDate;
import java.util.List;


public interface IVentaService {
    
    // CRUD Venta
    void saveVenta(VentaDTO datosVenta, Long id_cliente);
    List<VentaMostrarDTO> getVentas();
    VentaMostrarDTO getVentaById(Long codigo_venta);
    void deleteVenta(Long codigo_venta);
    VentaMostrarDTO editVenta(Long codigo_venta, VentaDTO ventaEditada, Long id_cliente);
    
    List<Producto> getVentaProductos(Long codigo_venta);
    ResumenVentasDTO getVentaResumeByDate(LocalDate fecha);
    Venta getHighestTotalVenta();
    
    // Descontar productos del stock y finalizar venta. NOTAR: No es necesario
    // tener el stock disponible al momento de crear una venta. Sólo al momento de 
    // concretarla
    void concretarVenta(Long codigo_venta);
    
    void saveMiVenta(VentaDTO datosVenta);
    List<VentaMostrarDTO> getMisVentas();
    VentaMostrarDTO getMiVentaById(Long codigo_venta);
    void deleteMiVenta(Long codigo_venta);
    
}

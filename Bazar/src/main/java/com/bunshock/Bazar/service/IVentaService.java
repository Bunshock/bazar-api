package com.bunshock.Bazar.service;

import com.bunshock.Bazar.dto.ResumenVentasDTO;
import com.bunshock.Bazar.dto.VentaDTO;
import com.bunshock.Bazar.model.Producto;
import com.bunshock.Bazar.model.Venta;
import java.time.LocalDate;
import java.util.List;


public interface IVentaService {
    
    // CRUD Venta
    void saveVenta(VentaDTO datosVenta);
    List<Venta> getVentas();
    Venta getVentaById(Long id);
    void deleteVenta(Long id);
    Venta editVenta(Long id, VentaDTO ventaEditada);
    
    List<Producto> getVentaProductos(Long id);
    ResumenVentasDTO getVentaResumeByDate(LocalDate fecha);
    
}

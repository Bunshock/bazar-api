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
    VentaMostrarDTO getVentaById(Long id);
    void deleteVenta(Long id);
    VentaMostrarDTO editVenta(Long id, VentaDTO ventaEditada, Long id_cliente);
    
    List<Producto> getVentaProductos(Long id);
    ResumenVentasDTO getVentaResumeByDate(LocalDate fecha);
    Venta getHighestTotalVenta();
    
    // Descontar productos del stock y finalizar venta. NOTAR: No es necesario
    // tener el stock disponible al momento de crear una venta. Sólo al momento de 
    // concretarla
    void concretarVenta(Long id);
    
    void saveMiVenta(VentaDTO datosVenta);
    List<VentaMostrarDTO> getMisVentas();
    // AGREGAR: Editar mis ventas (si no fueron concretadas todavía) /mis-ventas/editar/{codigo_venta}
    //          Borrar mis ventas (si no fueron concretadas todavía) /mis-ventas/eliminar/{codigo_venta}
    //          Consultar mis ventas individuales   /mis-ventas/{codigo_venta}
    
}

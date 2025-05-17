package com.bunshock.Bazar.utils;

import com.bunshock.Bazar.dto.VentaMostrarDTO;
import com.bunshock.Bazar.model.Venta;


public interface IServiceUtils {
    
    VentaMostrarDTO mapVentaToVentaMostrarDTO(Venta venta);
    
}

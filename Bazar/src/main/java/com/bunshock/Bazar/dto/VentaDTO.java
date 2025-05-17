package com.bunshock.Bazar.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class VentaDTO {
    
    @NotNull(message = "La fecha de venta no puede ser nula", groups = OnCreate.class)
    @PastOrPresent(message = "La fecha de venta no debe ser en el futuro",
            groups = {OnCreate.class, OnUpdate.class})
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fecha_venta;
    
    @NotNull(message = "El total no puede ser nulo", groups = OnCreate.class)
    @Positive(message = "El total debe ser un valor numérico positivo",
            groups = {OnCreate.class, OnUpdate.class})
    private Double total;
    
    @NotEmpty(message = "La lista de productos de la venta no puede ser nula o"
            + " vacía", groups = {OnCreate.class})
    private List<Long> listaIdProductos;

    public VentaDTO() {
    }

    public VentaDTO(LocalDate fecha_venta, Double total, List<Long> listaIdProductos,
            Long idCliente) {
        this.fecha_venta = fecha_venta;
        this.total = total;
        this.listaIdProductos = listaIdProductos;
    }
    
}

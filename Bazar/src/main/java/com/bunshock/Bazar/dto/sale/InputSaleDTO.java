package com.bunshock.Bazar.dto.sale;

import com.bunshock.Bazar.dto.OnCreate;
import com.bunshock.Bazar.dto.OnUpdate;
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
public class InputSaleDTO {
    
    @NotNull(message = "La fecha de venta no puede ser nula", groups = OnCreate.class)
    @PastOrPresent(message = "La fecha de venta no debe ser en el futuro",
            groups = {OnCreate.class, OnUpdate.class})
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate saleDate;
    
    @NotNull(message = "El total no puede ser nulo", groups = OnCreate.class)
    @Positive(message = "El total debe ser un valor numérico positivo",
            groups = {OnCreate.class, OnUpdate.class})
    private Double totalPrice;
    
    @NotEmpty(message = "La lista de productos de la venta no puede ser nula o"
            + " vacía", groups = {OnCreate.class})
    private List<Long> productCodeList;

    public InputSaleDTO() {
    }

    public InputSaleDTO(LocalDate saleDate, Double totalPrice, List<Long> productCodeList) {
        this.saleDate = saleDate;
        this.totalPrice = totalPrice;
        this.productCodeList = productCodeList;
    }
    
}

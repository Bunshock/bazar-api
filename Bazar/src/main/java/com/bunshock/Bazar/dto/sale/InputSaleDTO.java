package com.bunshock.Bazar.dto.sale;

import com.bunshock.Bazar.dto.OnCreate;
import com.bunshock.Bazar.dto.OnUpdate;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
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
    
    @NotEmpty(message = "La lista de productos de la venta no puede ser nula o"
            + " vacía", groups = {OnCreate.class})
    private List<Long> productCodeList;

    public InputSaleDTO() {
    }

    public InputSaleDTO(LocalDate saleDate, List<Long> productCodeList) {
        this.saleDate = saleDate;
        this.productCodeList = productCodeList;
    }
    
}

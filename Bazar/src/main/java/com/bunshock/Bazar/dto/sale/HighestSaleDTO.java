package com.bunshock.Bazar.dto.sale;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class HighestSaleDTO {
    
    // Datos de Venta
    private Long saleCode;
    private Double totalPrice;
    private int productCount;
    
    // Datos de Cliente
    private String clientFirstName;
    private String clientLastName;

    public HighestSaleDTO() {
    }

    public HighestSaleDTO(Long saleCode, Double totalPrice, int productCount,
            String clientFirstName, String clientLastName) {
        this.saleCode = saleCode;
        this.totalPrice = totalPrice;
        this.productCount = productCount;
        this.clientFirstName = clientFirstName;
        this.clientLastName = clientLastName;
    }
    
}

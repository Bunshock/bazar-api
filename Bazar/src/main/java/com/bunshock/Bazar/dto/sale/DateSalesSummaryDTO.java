package com.bunshock.Bazar.dto.sale;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class DateSalesSummaryDTO {
    
    private Double totalPrice;
    private int saleCount;

    public DateSalesSummaryDTO() {
    }

    public DateSalesSummaryDTO(Double totalPrice, int saleCount) {
        this.totalPrice = totalPrice;
        this.saleCount = saleCount;
    }    
    
}

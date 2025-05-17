package com.bunshock.Bazar.dto.product;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class SaleProductDTO {
    
    private String name;
    private String brand;
    private Double price;
    private Double amountBought;

    public SaleProductDTO() {
    }

    public SaleProductDTO(String name, String brand, Double price, Double amountBought) {
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.amountBought = amountBought;
    }
    
}

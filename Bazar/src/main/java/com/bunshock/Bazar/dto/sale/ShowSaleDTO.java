package com.bunshock.Bazar.dto.sale;

import com.bunshock.Bazar.dto.product.SaleProductDTO;
import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class ShowSaleDTO {
    
    private Long saleCode;
    private LocalDate saleDate;
    private Double totalPrice;
    private boolean finalized;
    
    private List<SaleProductDTO> productsBought;
    
    private Long clientId;

    public ShowSaleDTO() {
    }

    public ShowSaleDTO(Long saleCode, LocalDate saleDate, Double totalPrice,
            boolean finalized, List<SaleProductDTO> productsBought, Long clientId) {
        this.saleCode = saleCode;
        this.saleDate = saleDate;
        this.totalPrice = totalPrice;
        this.finalized = finalized;
        this.productsBought = productsBought;
        this.clientId = clientId;
    }

    
    
}

package com.bunshock.Bazar.exception.app;


public class SaleNotFoundException extends AppNotFoundException {

    public SaleNotFoundException(String action, Long saleCode) {
        super(action, "venta", "código", saleCode);
    }
    
}

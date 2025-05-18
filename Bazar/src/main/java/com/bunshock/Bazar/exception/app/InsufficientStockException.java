package com.bunshock.Bazar.exception.app;


public class InsufficientStockException extends AppBusinessException {

    public InsufficientStockException(String action, Long productCode, String productName) {
        super(action, "stock insuficiente del producto " + productName + " (código: " + productCode + ")");
    }
    
}

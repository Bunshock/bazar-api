package com.bunshock.Bazar.exception.app;


public class ProductNotFoundException extends AppNotFoundException {

    public ProductNotFoundException(String action, Long productCode) {
        super(action, "producto", "código", productCode);
    }
    
}

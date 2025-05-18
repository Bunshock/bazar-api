package com.bunshock.Bazar.exception.app;


public class FinalizeSaleException extends AppBusinessException {

    public FinalizeSaleException(String action, Long saleCode) {
        super(action, "la venta con código " + saleCode + " ya fue concretada");
    }
    
}

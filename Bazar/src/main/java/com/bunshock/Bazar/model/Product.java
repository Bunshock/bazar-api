package com.bunshock.Bazar.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter @Setter
@Table(name = "products")
public class Product {
    
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    @Column(name = "product_code")
    private Long productCode;
    private String name;
    private String brand;
    private Double price;
    @Column(name = "amount_available")
    private Double amountAvailable;
    // Sería de utilidad saber todas las ventas asociadas a determinado producto?
    // Sí, pero lo podemos hacer consultando a Venta.
    // Decisión: No incluimos @ManyToMany del lado de Producto (en relacion a Venta)
    
    public Product() {
    }

    public Product(Long productCode, String name, String brand, Double price,
            Double amountAvailable) {
        this.productCode = productCode;
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.amountAvailable = amountAvailable;
    }
    
}

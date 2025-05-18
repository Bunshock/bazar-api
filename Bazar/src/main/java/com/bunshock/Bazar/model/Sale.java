package com.bunshock.Bazar.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter @Setter
@Table(name = "sales")
public class Sale {
    
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    @Column(name = "sale_code")
    private Long saleCode;
    @Column(name = "sale_date")
    private LocalDate saleDate;
    @Column(name = "total_price")
    private Double totalPrice;
    private boolean finalized;
    // Relación con Producto. Definimos la tabla de union.
    @ManyToMany
    @JoinTable(
            name = "sale_product",
            joinColumns = @JoinColumn(name = "sales_sale_code"),
            inverseJoinColumns = @JoinColumn(name = "products_product_code")
    )
    private List<Product> productList;
    // Relación con Cliente
    @ManyToOne
    @JoinColumn(name = "clients_id_client")
    private Client client;

    public Sale() {
    }

    public Sale(Long saleCode, LocalDate saleDate, Double totalPrice,
            List<Product> productList, Client client) {
        this.saleCode = saleCode;
        this.saleDate = saleDate;
        this.totalPrice = totalPrice;
        this.finalized = false;
        this.productList = productList;
        this.client = client;
    }
    
}

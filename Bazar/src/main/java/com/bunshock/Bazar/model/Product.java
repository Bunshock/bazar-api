package com.bunshock.Bazar.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter @Setter
public class Product {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private Long codigo_producto;
    private String nombre;
    private String marca;
    private Double costo;
    @Column(name = "cantidad_disponible")
    private Double cantidadDisponible;
    // Sería de utilidad saber todas las ventas asociadas a determinado producto?
    // Sí, pero lo podemos hacer consultando a Venta.
    // Decisión: No incluimos @ManyToMany del lado de Producto (en relacion a Venta)
    
    public Product() {
    }

    public Product(Long codigo_producto, String nombre, String marca,
            Double costo, Double cantidadDisponible) {
        this.codigo_producto = codigo_producto;
        this.nombre = nombre;
        this.marca = marca;
        this.costo = costo;
        this.cantidadDisponible = cantidadDisponible;
    }
    
}

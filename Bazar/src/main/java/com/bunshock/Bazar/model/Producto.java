package com.bunshock.Bazar.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import java.util.List;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter @Setter
public class Producto {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private Long codigo_producto;
    private String nombre;
    private String marca;
    private Double costo;
    private Double cantidad_disponible;
    // Sería de utilidad saber todas las ventas asociadas a determinado producto.
    // Hacemos la relacion entre Venta y Producto bidireccional.
    @ManyToMany(mappedBy="listaProductos")
    private List<Venta> ventasProducto;
    
    public Producto() {
    }

    public Producto(Long codigo_producto, String nombre, String marca,
            Double costo, Double cantidad_disponible, List<Venta> ventasProducto) {
        this.codigo_producto = codigo_producto;
        this.nombre = nombre;
        this.marca = marca;
        this.costo = costo;
        this.cantidad_disponible = cantidad_disponible;
        this.ventasProducto = ventasProducto;
    }
    
}

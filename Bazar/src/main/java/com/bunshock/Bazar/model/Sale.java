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
import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter @Setter
public class Sale {
    
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    @Column(name = "codigo_venta")
    private Long codigoVenta;
    private LocalDate fecha_venta;
    private Double total;
    private boolean realizada;
    // Relación con Producto. Definimos la tabla de union.
    @ManyToMany
    @JoinTable(
            name = "venta_producto",
            joinColumns = @JoinColumn(name="venta_codigo_venta"),
            inverseJoinColumns = @JoinColumn(name="producto_codigo_producto")
    )
    private List<Product> listaProductos;
    // Relación con Cliente
    @ManyToOne
    @JoinColumn(name = "cliente_id_cliente")
    private Client unCliente;

    public Sale() {
    }

    public Sale(Long codigoVenta, LocalDate fecha_venta, Double total,
            List<Product> listaProductos, Client unCliente) {
        this.codigoVenta = codigoVenta;
        this.fecha_venta = fecha_venta;
        this.realizada = false;
        this.total = total;
        this.listaProductos = listaProductos;
        this.unCliente = unCliente;
    }
    
}

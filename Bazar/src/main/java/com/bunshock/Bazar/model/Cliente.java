package com.bunshock.Bazar.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.List;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter @Setter
public class Cliente {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private Long id_cliente;
    private String nombre;
    private String apellido;
    private String dni;
    // Sería de utilidad que el cliente sepa cuales fueron sus ventas
    // asociadas. Hacemos la relación bidireccional con Venta.
    // Ademas, notar que una venta no puede existir sin un cliente.
    // Decision: si borramos un cliente, borramos todas sus ventas asociadas.
    @OneToMany(mappedBy="unCliente", cascade=CascadeType.REMOVE, orphanRemoval=true)
    private List<Venta> ventasCliente;

    public Cliente() {
    }

    public Cliente(Long id_cliente, String nombre, String apellido, String dni,
            List<Venta> ventasCliente) {
        this.id_cliente = id_cliente;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.ventasCliente = ventasCliente;
    }
    
}

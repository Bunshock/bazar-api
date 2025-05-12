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
    // Sería de utilidad que el cliente sepa cuales fueron sus ventas? No tanto,
    // si queremos saber las ventas de un cliente, consultamos la entidad Venta
    // y filtramos por id del Cliente deseado.
    // Decisión: No definimos @OneToMany del lado del Cliente

    public Cliente() {
    }

    public Cliente(Long id_cliente, String nombre, String apellido, String dni) {
        this.id_cliente = id_cliente;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
    }
    
}

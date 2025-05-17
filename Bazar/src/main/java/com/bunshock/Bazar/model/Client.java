package com.bunshock.Bazar.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter @Setter
public class Client {
    
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    @Column(name = "id_cliente")
    private Long idCliente;
    private String nombre;
    private String apellido;
    private String dni;
    // Sería de utilidad que el cliente sepa cuales fueron sus ventas? No tanto:
    // si queremos saber las ventas de un cliente, consultamos la entidad Venta
    // y filtramos por id del Cliente deseado.
    // Decisión: No definimos @OneToMany del lado del Cliente
    
    // Puede haber clientes no registrados
    @OneToOne(optional = true)
    @JoinColumn(name = "users_id")
    private UserEntity usuario;

    public Client() {
    }

    public Client(Long idCliente, String nombre, String apellido, String dni,
            UserEntity usuario) {
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.usuario = usuario;
    }
    
}

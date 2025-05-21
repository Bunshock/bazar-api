package com.bunshock.Bazar.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter @Setter
@Table(name = "clients")
public class Client {
    
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    @Column(name = "id_client")
    private Long idClient;
    private String firstName;
    private String lastName;
    private String dni;
    // Sería de utilidad que el cliente sepa cuales fueron sus ventas? No tanto:
    // si queremos saber las ventas de un cliente, consultamos la entidad Venta
    // y filtramos por id del Cliente deseado.
    // Decisión: No definimos @OneToMany del lado del Cliente
    
    // Puede haber clientes no registrados
    @OneToOne(optional = true)
    @JoinColumn(name = "users_id")
    private UserEntity user;

    public Client() {
    }

    public Client(String firstName, String lastName, String dni, UserEntity user) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dni = dni;
        this.user = user;
    }
    
}

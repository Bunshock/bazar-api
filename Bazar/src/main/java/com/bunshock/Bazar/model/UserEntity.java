package com.bunshock.Bazar.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity {
    
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String username;
    private String password;
    
    @Column(name = "is_enabled")
    private boolean isEnabled;
    
    @Column(name = "account_no_expired")
    private boolean accountNonExpired;
    
    @Column(name = "account_no_locked")
    private boolean accountNonLocked;
    
    @Column(name = "credential_no_expired")
    private boolean credentialsNonExpired;
    
    // Relacion unidireccional con RoleEntity. Un usuario puede tener varios roles.
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "users_id"),
            inverseJoinColumns = @JoinColumn(name = "roles_id"))
    private Set<RoleEntity> roleSet;
    
    // Relación con Cliente. Al crear un usuario, se crea un Cliente vacio
    // asociado al usuario. Puede haber usuarios sin cliente asociado (como 'admin')
    @OneToOne(optional = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

}

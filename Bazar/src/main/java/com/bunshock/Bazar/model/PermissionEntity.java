package com.bunshock.Bazar.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Entity
@Builder
@Getter @Setter
@Table(name = "permissions")
public class PermissionEntity {
    
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String name;

    public PermissionEntity() {
    }

    public PermissionEntity(Long id, String name) {
        this.id = id;
        this.name = name;
    }
    
}

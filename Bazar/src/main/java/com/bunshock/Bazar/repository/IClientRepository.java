package com.bunshock.Bazar.repository;

import com.bunshock.Bazar.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IClientRepository extends JpaRepository<Client, Long> {
    
}

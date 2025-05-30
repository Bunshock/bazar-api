package com.bunshock.Bazar.repository;

import com.bunshock.Bazar.model.Product;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IProductRepository extends JpaRepository<Product, Long> {
    
    List<Product> findByAmountAvailableLessThan(Double amount);
    
}

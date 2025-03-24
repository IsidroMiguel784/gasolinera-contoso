package com.contoso.gasolinera.repository;

import com.contoso.gasolinera.model.ProductoSurtidor;
import com.contoso.gasolinera.model.Surtidor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoSurtidorRepository extends JpaRepository<ProductoSurtidor, Long> {
    List<ProductoSurtidor> findBySurtidor(Surtidor surtidor);
}


package com.contoso.gasolinera.repository;

import com.contoso.gasolinera.model.Tanque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TanqueRepository extends JpaRepository<Tanque, Long> {
    Optional<Tanque> findByTipoProducto(String tipoProducto);
}



package com.contoso.gasolinera.repository;

import com.contoso.gasolinera.model.PrecioProducto;
import com.contoso.gasolinera.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.YearMonth;
import java.util.Optional;

@Repository
public interface PrecioProductoRepository extends JpaRepository<PrecioProducto, Long> {
    Optional<PrecioProducto> findByProductoAndMes(Producto producto, YearMonth mes);
}


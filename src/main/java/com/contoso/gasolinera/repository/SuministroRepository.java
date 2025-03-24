package com.contoso.gasolinera.repository;

import com.contoso.gasolinera.model.Suministro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SuministroRepository extends JpaRepository<Suministro, Long> {
    List<Suministro> findByFechaBetween(LocalDateTime inicio, LocalDateTime fin);
}


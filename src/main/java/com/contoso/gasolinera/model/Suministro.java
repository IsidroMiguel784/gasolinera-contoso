package com.contoso.gasolinera.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Suministro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime fecha;

    @ManyToOne
    @JoinColumn(name = "surtidor_id")
    private Surtidor surtidor;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;

    private double litros;
    private BigDecimal importe;
}


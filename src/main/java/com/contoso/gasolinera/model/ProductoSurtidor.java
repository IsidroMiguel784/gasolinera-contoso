package com.contoso.gasolinera.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoSurtidor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "surtidor_id")
    private Surtidor surtidor;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;
}


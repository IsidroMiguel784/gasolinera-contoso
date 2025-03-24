package com.contoso.gasolinera.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Surtidor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String codigo;
    private boolean activo;

    @OneToMany(mappedBy = "surtidor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductoSurtidor> productos;
}


package com.contoso.gasolinera.controller;

import com.contoso.gasolinera.model.Producto;
import com.contoso.gasolinera.model.Suministro;
import com.contoso.gasolinera.model.Surtidor;
import com.contoso.gasolinera.service.ProductoService;
import com.contoso.gasolinera.service.SuministroService;
import com.contoso.gasolinera.service.SurtidorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/suministros")
public class SuministroController {

    @Autowired
    private SuministroService suministroService;

    @Autowired
    private SurtidorService surtidorService;

    @Autowired
    private ProductoService productoService;

    @GetMapping
    public ResponseEntity<List<Suministro>> obtenerTodosSuministros() {
        return ResponseEntity.ok(suministroService.obtenerTodosSuministros());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Suministro> obtenerSuministroPorId(@PathVariable Long id) {
        Optional<Suministro> suministro = suministroService.obtenerSuministroPorId(id);
        return suministro.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/periodo")
    public ResponseEntity<List<Suministro>> obtenerSuministrosPorPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fin) {

        return ResponseEntity.ok(suministroService.obtenerSuministrosPorPeriodo(inicio, fin));
    }

    @PostMapping
    public ResponseEntity<Suministro> registrarSuministro(
            @RequestParam Long surtidorId,
            @RequestParam Long productoId,
            @RequestParam double litros) {

        Optional<Surtidor> surtidorOpt = surtidorService.obtenerSurtidorPorId(surtidorId);
        Optional<Producto> productoOpt = productoService.obtenerProductoPorId(productoId);

        if (surtidorOpt.isEmpty() || productoOpt.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        Optional<Suministro> suministroOpt = suministroService.registrarSuministro(
                surtidorOpt.get(), productoOpt.get(), litros);

        return suministroOpt.map(suministro -> ResponseEntity.status(HttpStatus.CREATED).body(suministro))
                .orElse(ResponseEntity.badRequest().build());
    }
}


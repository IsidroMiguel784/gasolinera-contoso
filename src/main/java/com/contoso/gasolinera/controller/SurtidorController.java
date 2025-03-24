package com.contoso.gasolinera.controller;

import com.contoso.gasolinera.model.Surtidor;
import com.contoso.gasolinera.service.SurtidorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/surtidores")
public class SurtidorController {

    @Autowired
    private SurtidorService surtidorService;

    @GetMapping
    public ResponseEntity<List<Surtidor>> obtenerTodosSurtidores() {
        return ResponseEntity.ok(surtidorService.obtenerTodosSurtidores());
    }

    @GetMapping("/activos")
    public ResponseEntity<List<Surtidor>> obtenerSurtidoresActivos() {
        return ResponseEntity.ok(surtidorService.obtenerSurtidoresActivos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Surtidor> obtenerSurtidorPorId(@PathVariable Long id) {
        Optional<Surtidor> surtidor = surtidorService.obtenerSurtidorPorId(id);
        return surtidor.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Surtidor> crearSurtidor(@RequestBody Surtidor surtidor) {
        Surtidor nuevoSurtidor = surtidorService.crearSurtidor(surtidor);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoSurtidor);
    }

    @PutMapping("/{id}/desactivar")
    public ResponseEntity<Void> desactivarSurtidor(@PathVariable Long id) {
        surtidorService.desactivarSurtidor(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/activar")
    public ResponseEntity<Void> activarSurtidor(@PathVariable Long id) {
        surtidorService.activarSurtidor(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/productos")
    public ResponseEntity<Void> asignarProductos(@PathVariable Long id, @RequestBody List<Long> productoIds) {
        surtidorService.asignarProductosASurtidor(id, productoIds);
        return ResponseEntity.ok().build();
    }
}



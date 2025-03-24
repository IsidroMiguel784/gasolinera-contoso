package com.contoso.gasolinera.controller;

import com.contoso.gasolinera.model.Tanque;
import com.contoso.gasolinera.service.TanqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tanques")
public class TanqueController {

    @Autowired
    private TanqueService tanqueService;

    @GetMapping
    public ResponseEntity<List<Tanque>> obtenerTodosTanques() {
        return ResponseEntity.ok(tanqueService.obtenerTodosTanques());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tanque> obtenerTanquePorId(@PathVariable Long id) {
        Optional<Tanque> tanque = tanqueService.obtenerTanquePorId(id);
        return tanque.map(ResponseEntity::ok)  tanque = tanqueService.obtenerTanquePorId(id);
        return tanque.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/tipo/{tipoProducto}")
    public ResponseEntity<Tanque> obtenerTanquePorTipoProducto(@PathVariable String tipoProducto) {
        Optional<Tanque> tanque = tanqueService.obtenerTanquePorTipoProducto(tipoProducto);
        return tanque.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Tanque> crearTanque(@RequestBody Tanque tanque) {
        Tanque nuevoTanque = tanqueService.crearTanque(tanque);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoTanque);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tanque> actualizarTanque(@PathVariable Long id, @RequestBody Tanque tanque) {
        Optional<Tanque> tanqueActualizado = tanqueService.actualizarTanque(id, tanque);
        return tanqueActualizado.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/recargar")
    public ResponseEntity<Void> recargarTanque(@PathVariable Long id, @RequestParam double litros) {
        boolean recargaExitosa = tanqueService.recargarTanque(id, litros);
        if (recargaExitosa) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }
}


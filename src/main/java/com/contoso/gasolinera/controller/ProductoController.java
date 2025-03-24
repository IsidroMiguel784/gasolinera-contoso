package com.contoso.gasolinera.controller;

import com.contoso.gasolinera.model.PrecioProducto;
import com.contoso.gasolinera.model.Producto;
import com.contoso.gasolinera.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping
    public ResponseEntity<List<Producto>> obtenerTodosProductos() {
        return ResponseEntity.ok(productoService.obtenerTodosProductos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerProductoPorId(@PathVariable Long id) {
        Optional<Producto> producto = productoService.obtenerProductoPorId(id);
        return producto.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Producto> crearProducto(@RequestBody Producto producto) {
        Producto nuevoProducto = productoService.crearProducto(producto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoProducto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizarProducto(@PathVariable Long id, @RequestBody Producto producto) {
        Optional<Producto> productoActualizado = productoService.actualizarProducto(id, producto);
        return productoActualizado.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id) {
        productoService.eliminarProducto(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/precio")
    public ResponseEntity<PrecioProducto> establecerPrecio(
            @PathVariable Long id,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM") YearMonth mes,
            @RequestParam BigDecimal precio) {

        PrecioProducto precioProducto = productoService.establecerPrecioProducto(id, mes, precio);
        if (precioProducto != null) {
            return ResponseEntity.ok(precioProducto);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}/precio")
    public ResponseEntity<PrecioProducto> obtenerPrecio(
            @PathVariable Long id,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM") YearMonth mes) {

        Optional<PrecioProducto> precioProducto = productoService.obtenerPrecioProducto(id, mes);
        return precioProducto.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}


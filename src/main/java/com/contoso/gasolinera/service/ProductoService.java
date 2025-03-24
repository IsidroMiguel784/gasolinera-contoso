package com.contoso.gasolinera.service;

import com.contoso.gasolinera.model.PrecioProducto;
import com.contoso.gasolinera.model.Producto;
import com.contoso.gasolinera.repository.PrecioProductoRepository;
import com.contoso.gasolinera.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private PrecioProductoRepository precioProductoRepository;

    public List<Producto> obtenerTodosProductos() {
        return productoRepository.findAll();
    }

    public Optional<Producto> obtenerProductoPorId(Long id) {
        return productoRepository.findById(id);
    }

    @Transactional
    public Producto crearProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    @Transactional
    public Optional<Producto> actualizarProducto(Long id, Producto productoActualizado) {
        Optional<Producto> productoOpt = productoRepository.findById(id);
        if (productoOpt.isPresent()) {
            Producto producto = productoOpt.get();
            producto.setNombre(productoActualizado.getNombre());
            producto.setTipo(productoActualizado.getTipo());
            producto.setTanque(productoActualizado.getTanque());
            return Optional.of(productoRepository.save(producto));
        }
        return Optional.empty();
    }

    @Transactional
    public void eliminarProducto(Long id) {
        productoRepository.deleteById(id);
    }

    @Transactional
    public PrecioProducto establecerPrecioProducto(Long productoId, YearMonth mes, BigDecimal precio) {
        Optional<Producto> productoOpt = productoRepository.findById(productoId);
        if (productoOpt.isPresent()) {
            Producto producto = productoOpt.get();

            // Buscar si ya existe un precio para este producto y mes
            Optional<PrecioProducto> precioExistenteOpt = precioProductoRepository.findByProductoAndMes(producto, mes);

            PrecioProducto precioProducto;
            if (precioExistenteOpt.isPresent()) {
                precioProducto = precioExistenteOpt.get();
                precioProducto.setPrecio(precio);
            } else {
                precioProducto = new PrecioProducto();
                precioProducto.setProducto(producto);
                precioProducto.setMes(mes);
                precioProducto.setPrecio(precio);
            }

            return precioProductoRepository.save(precioProducto);
        }
        return null;
    }

    public Optional<PrecioProducto> obtenerPrecioProducto(Long productoId, YearMonth mes) {
        Optional<Producto> productoOpt = productoRepository.findById(productoId);
        if (productoOpt.isPresent()) {
            return precioProductoRepository.findByProductoAndMes(productoOpt.get(), mes);
        }
        return Optional.empty();
    }
}


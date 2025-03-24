package com.contoso.gasolinera.service;

import com.contoso.gasolinera.model.*;
import com.contoso.gasolinera.repository.PrecioProductoRepository;
import com.contoso.gasolinera.repository.SuministroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

@Service
public class SuministroService {

    @Autowired
    private SuministroRepository suministroRepository;

    @Autowired
    private PrecioProductoRepository precioProductoRepository;

    @Autowired
    private TanqueService tanqueService;

    public List<Suministro> obtenerTodosSuministros() {
        return suministroRepository.findAll();
    }

    public Optional<Suministro> obtenerSuministroPorId(Long id) {
        return suministroRepository.findById(id);
    }

    public List<Suministro> obtenerSuministrosPorPeriodo(LocalDateTime inicio, LocalDateTime fin) {
        return suministroRepository.findByFechaBetween(inicio, fin);
    }

    @Transactional
    public Optional<Suministro> registrarSuministro(Surtidor surtidor, Producto producto, double litros) {
        // Verificar que el surtidor esté activo
        if (!surtidor.isActivo()) {
            return Optional.empty();
        }

        // Obtener el tanque asociado al producto
        Tanque tanque = producto.getTanque();
        if (tanque == null) {
            return Optional.empty();
        }

        // Verificar y consumir combustible del tanque
        boolean consumoExitoso = tanqueService.consumirCombustible(tanque.getId(), litros);
        if (!consumoExitoso) {
            return Optional.empty();
        }

        // Obtener el precio actual del producto
        YearMonth mesActual = YearMonth.now();
        Optional<PrecioProducto> precioOpt = precioProductoRepository.findByProductoAndMes(producto, mesActual);

        if (precioOpt.isEmpty()) {
            // No hay precio definido para este mes
            return Optional.empty();
        }

        BigDecimal precioPorLitro = precioOpt.get().getPrecio();
        BigDecimal importe = precioPorLitro.multiply(BigDecimal.valueOf(litros));

        // Crear y guardar el suministro
        Suministro suministro = new Suministro();
        suministro.setFecha(LocalDateTime.now());
        suministro.setSurtidor(surtidor);
        suministro.setProducto(producto);
        suministro.setLitros(litros);
        suministro.setImporte(importe);

        return Optional.of(suministroRepository.save(suministro));
    }
}


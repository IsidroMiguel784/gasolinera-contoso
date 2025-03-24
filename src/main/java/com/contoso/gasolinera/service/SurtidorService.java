package com.contoso.gasolinera.service;

import com.contoso.gasolinera.model.Producto;
import com.contoso.gasolinera.model.ProductoSurtidor;
import com.contoso.gasolinera.model.Surtidor;
import com.contoso.gasolinera.repository.ProductoRepository;
import com.contoso.gasolinera.repository.ProductoSurtidorRepository;
import com.contoso.gasolinera.repository.SurtidorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SurtidorService {

    @Autowired
    private SurtidorRepository surtidorRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private ProductoSurtidorRepository productoSurtidorRepository;

    public List<Surtidor> obtenerTodosSurtidores() {
        return surtidorRepository.findAll();
    }

    public List<Surtidor> obtenerSurtidoresActivos() {
        return surtidorRepository.findByActivo(true);
    }

    public Optional<Surtidor> obtenerSurtidorPorId(Long id) {
        return surtidorRepository.findById(id);
    }

    @Transactional
    public Surtidor crearSurtidor(Surtidor surtidor) {
        surtidor.setActivo(true);
        return surtidorRepository.save(surtidor);
    }

    @Transactional
    public void desactivarSurtidor(Long id) {
        Optional<Surtidor> surtidorOpt = surtidorRepository.findById(id);
        if (surtidorOpt.isPresent()) {
            Surtidor surtidor = surtidorOpt.get();
            surtidor.setActivo(false);
            surtidorRepository.save(surtidor);
        }
    }

    @Transactional
    public void activarSurtidor(Long id) {
        Optional<Surtidor> surtidorOpt = surtidorRepository.findById(id);
        if (surtidorOpt.isPresent()) {
            Surtidor surtidor = surtidorOpt.get();
            surtidor.setActivo(true);
            surtidorRepository.save(surtidor);
        }
    }

    @Transactional
    public void asignarProductosASurtidor(Long surtidorId, List<Long> productoIds) {
        Optional<Surtidor> surtidorOpt = surtidorRepository.findById(surtidorId);
        if (surtidorOpt.isPresent()) {
            Surtidor surtidor = surtidorOpt.get();

            // Eliminar asignaciones existentes
            List<ProductoSurtidor> existentes = productoSurtidorRepository.findBySurtidor(surtidor);
            productoSurtidorRepository.deleteAll(existentes);

            // Crear nuevas asignaciones
            List<ProductoSurtidor> nuevasAsignaciones = new ArrayList<>();
            for (Long productoId : productoIds) {
                Optional<Producto> productoOpt = productoRepository.findById(productoId);
                if (productoOpt.isPresent()) {
                    ProductoSurtidor ps = new ProductoSurtidor();
                    ps.setSurtidor(surtidor);
                    ps.setProducto(productoOpt.get());
                    nuevasAsignaciones.add(ps);
                }
            }

            productoSurtidorRepository.saveAll(nuevasAsignaciones);
        }
    }
}


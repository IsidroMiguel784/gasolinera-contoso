package com.contoso.gasolinera.service;

import com.contoso.gasolinera.model.Tanque;
import com.contoso.gasolinera.repository.TanqueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TanqueService {

    @Autowired
    private TanqueRepository tanqueRepository;

    public List<Tanque> obtenerTodosTanques() {
        return tanqueRepository.findAll();
    }

    public Optional<Tanque> obtenerTanquePorId(Long id) {
        return tanqueRepository.findById(id);
    }

    public Optional<Tanque> obtenerTanquePorTipoProducto(String tipoProducto) {
        return tanqueRepository.findByTipoProducto(tipoProducto);
    }

    @Transactional
    public Tanque crearTanque(Tanque tanque) {
        // Establecer capacidad máxima por defecto
        if (tanque.getCapacidadMaxima() <= 0) {
            tanque.setCapacidadMaxima(10000.0);
        }
        return tanqueRepository.save(tanque);
    }

    @Transactional
    public Optional<Tanque> actualizarTanque(Long id, Tanque tanqueActualizado) {
        Optional<Tanque> tanqueOpt = tanqueRepository.findById(id);
        if (tanqueOpt.isPresent()) {
            Tanque tanque = tanqueOpt.get();
            tanque.setCodigo(tanqueActualizado.getCodigo());
            tanque.setTipoProducto(tanqueActualizado.getTipoProducto());
            tanque.setCapacidadActual(tanqueActualizado.getCapacidadActual());
            return Optional.of(tanqueRepository.save(tanque));
        }
        return Optional.empty();
    }

    @Transactional
    public boolean recargarTanque(Long id, double litros) {
        Optional<Tanque> tanqueOpt = tanqueRepository.findById(id);
        if (tanqueOpt.isPresent()) {
            Tanque tanque = tanqueOpt.get();
            double nuevaCapacidad = tanque.getCapacidadActual() + litros;

            // Verificar que no exceda la capacidad máxima
            if (nuevaCapacidad <= tanque.getCapacidadMaxima()) {
                tanque.setCapacidadActual(nuevaCapacidad);
                tanqueRepository.save(tanque);
                return true;
            }
        }
        return false;
    }

    @Transactional
    public boolean consumirCombustible(Long id, double litros) {
        Optional<Tanque> tanqueOpt = tanqueRepository.findById(id);
        if (tanqueOpt.isPresent()) {
            Tanque tanque = tanqueOpt.get();

            // Verificar que haya suficiente combustible
            if (tanque.getCapacidadActual() >= litros) {
                tanque.setCapacidadActual(tanque.getCapacidadActual() - litros);
                tanqueRepository.save(tanque);
                return true;
            }
        }
        return false;
    }
}


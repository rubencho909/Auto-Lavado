package com.sena.lavadero.service.impl;

import com.sena.lavadero.entities.Movimiento;
import com.sena.lavadero.repository.MovimientoRepository;
import com.sena.lavadero.service.IMovimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovimientoServiceImpl implements IMovimientoService {

    @Autowired
    private MovimientoRepository movimientoRepository;

    @Override
    public void save(Movimiento movimiento) {
        movimientoRepository.save(movimiento);
    }

    @Override
    public List<Movimiento> listarMovimientos() {
        return movimientoRepository.findAll();
    }

    @Override
    public Movimiento movimientoPorId(Long id) {
        return movimientoRepository.findById(id).orElse(null);
    }

    @Override
    public void eliminarMovimiento(Long id) {
        movimientoRepository.deleteById(id);
    }
}

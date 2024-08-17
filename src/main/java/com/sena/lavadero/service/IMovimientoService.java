package com.sena.lavadero.service;

import com.sena.lavadero.entities.Movimiento;

import java.util.List;

public interface IMovimientoService {

    public void save(Movimiento movimiento);
    public List<Movimiento> listarMovimientos();
    public Movimiento movimientoPorId(Long id);
    public void eliminarMovimiento(Long id);

}

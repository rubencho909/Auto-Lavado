package com.sena.lavadero.service;

import com.sena.lavadero.entities.Servicio;

import java.util.List;
import java.util.Optional;

public interface IServiciosService {

    public void save(Servicio servicio);
    public List<Servicio> listarServicios();
    public Servicio servicioPorId(Long id);
    public Optional<Servicio> cargarPorId(Long id);
    public void eliminarServicio(Long id);
    public Servicio servicioPorTitulo(String titulo);

}

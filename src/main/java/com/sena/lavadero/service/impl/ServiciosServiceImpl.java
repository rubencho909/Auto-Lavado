package com.sena.lavadero.service.impl;

import com.sena.lavadero.entities.Servicio;
import com.sena.lavadero.repository.ServicioRepository;
import com.sena.lavadero.service.IServiciosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServiciosServiceImpl implements IServiciosService {

    @Autowired
    private ServicioRepository servicioRepository;

    @Override
    public void save(Servicio servicio) {
        servicioRepository.save(servicio);
    }

    @Override
    public List<Servicio> listarServicios() {
        return servicioRepository.findAll();
    }

    @Override
    public Servicio servicioPorId(Long id) {
        return servicioRepository.findById(id).orElse(null);
    }

    @Override
    public Optional<Servicio> cargarPorId(Long id) {
        return servicioRepository.findById(id);
    }

    @Override
    public void eliminarServicio(Long id) {
        servicioRepository.deleteById(id);
    }

    @Override
    public Servicio servicioPorTitulo(String titulo) {
        return servicioRepository.findByTitulo(titulo);
    }
}

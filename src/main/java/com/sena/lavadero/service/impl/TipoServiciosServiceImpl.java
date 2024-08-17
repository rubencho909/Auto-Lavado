package com.sena.lavadero.service.impl;

import com.sena.lavadero.entities.TipoServicio;
import com.sena.lavadero.repository.TipoServicioRepository;
import com.sena.lavadero.service.ITipoServiciosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TipoServiciosServiceImpl implements ITipoServiciosService {

    @Autowired
    private TipoServicioRepository tipoServicioRepository;

    @Override
    public void saveTipoServicio(TipoServicio tipoServicio) {
        tipoServicioRepository.save(tipoServicio);
    }

    @Override
    public TipoServicio obtenerTipo(Long id) {
        return tipoServicioRepository.findById(id).orElse(null);
    }

    @Override
    public void eliminarTipoServicio(Long id) {
        tipoServicioRepository.deleteById(id);
    }
}

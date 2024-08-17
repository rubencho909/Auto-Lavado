package com.sena.lavadero.service;

import com.sena.lavadero.entities.Rol;
import com.sena.lavadero.entities.TipoConcepto;
import com.sena.lavadero.repository.TipoConceptoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TipoConceptoService {

    @Autowired
    private TipoConceptoRepository tipoConceptoRepository;

    public List<TipoConcepto> listadoConceptos() {
        return tipoConceptoRepository.findAll();
    }

    public Optional<TipoConcepto> getTipoCOnceptoById(Long id) {
        return tipoConceptoRepository.findById(id);
    }

}

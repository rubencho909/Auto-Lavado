package com.sena.lavadero.service.impl;

import com.sena.lavadero.entities.Comentario;
import com.sena.lavadero.repository.ComentarioRepository;
import com.sena.lavadero.service.IComentarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ComentarioServicioImpl implements IComentarioService {

    @Autowired
    private ComentarioRepository comentarioRepository;

    @Override
    public void saveComentario(Comentario comentario) {
        comentarioRepository.save(comentario);
    }

    @Override
    public Comentario buscarPorId(Long id) {
        return comentarioRepository.findById(id).orElse(null);
    }

    @Override
    public void eliminarComentario(Long id) {
        comentarioRepository.deleteById(id);
    }
}

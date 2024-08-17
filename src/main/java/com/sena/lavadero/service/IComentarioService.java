package com.sena.lavadero.service;

import com.sena.lavadero.entities.Comentario;

public interface IComentarioService {

    public void saveComentario(Comentario comentario);
    public Comentario buscarPorId(Long id);
    public void eliminarComentario(Long id);

}

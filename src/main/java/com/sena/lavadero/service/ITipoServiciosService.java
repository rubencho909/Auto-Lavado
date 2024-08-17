package com.sena.lavadero.service;

import com.sena.lavadero.entities.TipoServicio;

public interface ITipoServiciosService {
    public void saveTipoServicio(TipoServicio tipoServicio);
    public TipoServicio obtenerTipo(Long id);
    public void  eliminarTipoServicio(Long id);
}

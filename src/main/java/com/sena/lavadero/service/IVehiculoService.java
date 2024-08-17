package com.sena.lavadero.service;

import com.sena.lavadero.entities.Servicio;
import com.sena.lavadero.entities.Vehiculo;

public interface IVehiculoService {

    public void save(Vehiculo vehiculo);
    public Vehiculo vehiculoPorId(Long id);
    public Vehiculo vehiculoPorPlaca(String placaVehiculo);

}

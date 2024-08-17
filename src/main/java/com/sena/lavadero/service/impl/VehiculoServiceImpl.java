package com.sena.lavadero.service.impl;

import com.sena.lavadero.entities.Vehiculo;
import com.sena.lavadero.repository.VehiculoRepository;
import com.sena.lavadero.service.IVehiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VehiculoServiceImpl implements IVehiculoService {

    @Autowired
    private VehiculoRepository vehiculoRepository;

    @Override
    public void save(Vehiculo vehiculo) {
        vehiculoRepository.save(vehiculo);
    }

    @Override
    public Vehiculo vehiculoPorId(Long id) {
        return vehiculoRepository.findById(id).orElse(null);
    }

    @Override
    public Vehiculo vehiculoPorPlaca(String placaVehiculo) {
        return vehiculoRepository.findByPlacaVehiculo(placaVehiculo);
    }
}

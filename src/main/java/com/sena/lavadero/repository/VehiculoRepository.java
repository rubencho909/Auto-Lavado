package com.sena.lavadero.repository;

import com.sena.lavadero.entities.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehiculoRepository extends JpaRepository<Vehiculo, Long> {

    Vehiculo findByPlacaVehiculo(String placaVehiculo);

}

package com.sena.lavadero.repository;

import com.sena.lavadero.entities.Servicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServicioRepository extends JpaRepository<Servicio, Long> {

    Servicio findByTitulo(String titulo);

}

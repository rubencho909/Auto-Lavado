package com.sena.lavadero.repository;

import com.sena.lavadero.entities.TipoConcepto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoConceptoRepository extends JpaRepository<TipoConcepto, Long> {
}

package com.sena.lavadero.repository;

import com.sena.lavadero.entities.Agenda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface IAgendaRepository extends JpaRepository<Agenda, Long> {

    @Query("SELECT COUNT(a) FROM Agenda a WHERE a.fechaAgenda = :fecha")
    Long countAgendasByFechaAgenda(@Param("fecha") LocalDate fecha);

}

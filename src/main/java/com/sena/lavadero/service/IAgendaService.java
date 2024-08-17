package com.sena.lavadero.service;

import com.sena.lavadero.entities.Agenda;
import com.sena.lavadero.entities.Movimiento;

import java.time.LocalDate;
import java.util.List;

public interface IAgendaService {

    public void save(Agenda agenda);
    public List<Agenda> listarAgendas();
    public Agenda agendaPorId(Long id);
    public void eliminarAgenda(Long id);
    public boolean agendarServicio(LocalDate fechaAgenda);

}

package com.sena.lavadero.service.impl;

import com.sena.lavadero.entities.Agenda;
import com.sena.lavadero.repository.IAgendaRepository;
import com.sena.lavadero.service.IAgendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AgendaServiceImpl implements IAgendaService {

    @Autowired
    private IAgendaRepository iAgendaRepository;

    @Override
    public void save(Agenda agenda) {
        iAgendaRepository.save(agenda);
    }

    @Override
    public List<Agenda> listarAgendas() {
        return iAgendaRepository.findAll();
    }

    @Override
    public Agenda agendaPorId(Long id) {
        return iAgendaRepository.findById(id).orElse(null);
    }

    @Override
    public void eliminarAgenda(Long id) {
        iAgendaRepository.deleteById(id);
    }

    @Override
    public boolean agendarServicio(LocalDate fechaAgenda) {
        Long count = iAgendaRepository.countAgendasByFechaAgenda(fechaAgenda);
        return count < 5;
    }

    /*
    public boolean validarLimiteAgendasPorFecha(LocalDate fechaAgenda) {
        Long count = iAgendaRepository.countAgendasByFechaAgenda(fechaAgenda);
        return count < 5;
    }
    */

}

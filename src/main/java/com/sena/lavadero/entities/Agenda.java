package com.sena.lavadero.entities;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Table(name = "agendas")
public class Agenda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "vehiculo_id")
    private Vehiculo vehiculo;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaAgenda;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_crea_id")
    private Usuario usuarioCrea;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_servicio_id")
    private Usuario usuarioServicio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "servicio_id")
    private Servicio servicio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipo_servicio_id")
    private TipoServicio tipoServicio;

    private String numeroTicket;

    public Agenda() {
    }

    public Agenda(Long id, Vehiculo vehiculo, LocalDate fechaAgenda, Usuario usuarioCrea, Usuario usuarioServicio, Servicio servicio, TipoServicio tipoServicio, String numeroTicket) {
        this.id = id;
        this.vehiculo = vehiculo;
        this.fechaAgenda = fechaAgenda;
        this.usuarioCrea = usuarioCrea;
        this.usuarioServicio = usuarioServicio;
        this.servicio = servicio;
        this.tipoServicio = tipoServicio;
        this.numeroTicket = numeroTicket;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    public LocalDate getFechaAgenda() {
        return fechaAgenda;
    }

    public void setFechaAgenda(LocalDate fechaAgenda) {
        this.fechaAgenda = fechaAgenda;
    }

    public Usuario getUsuarioCrea() {
        return usuarioCrea;
    }

    public void setUsuarioCrea(Usuario usuarioCrea) {
        this.usuarioCrea = usuarioCrea;
    }

    public Usuario getUsuarioServicio() {
        return usuarioServicio;
    }

    public void setUsuarioServicio(Usuario usuarioServicio) {
        this.usuarioServicio = usuarioServicio;
    }

    public Servicio getServicio() {
        return servicio;
    }

    public void setServicio(Servicio servicio) {
        this.servicio = servicio;
    }

    public TipoServicio getTipoServicio() {
        return tipoServicio;
    }

    public void setTipoServicio(TipoServicio tipoServicio) {
        this.tipoServicio = tipoServicio;
    }

    public String getNumeroTicket() {
        return numeroTicket;
    }

    public void setNumeroTicket(String numeroTicket) {
        this.numeroTicket = numeroTicket;
    }
}

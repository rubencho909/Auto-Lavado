package com.sena.lavadero.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "vehiculos")
public class Vehiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipoVehiculo;

    @Column(unique = true)
    private String placaVehiculo;

    public Vehiculo() {
    }

    public Vehiculo(Long id, String tipoVehiculo, String placaVehiculo) {
        this.id = id;
        this.tipoVehiculo = tipoVehiculo;
        this.placaVehiculo = placaVehiculo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipoVehiculo() {
        return tipoVehiculo;
    }

    public void setTipoVehiculo(String tipoVehiculo) {
        this.tipoVehiculo = tipoVehiculo;
    }

    public String getPlacaVehiculo() {
        return placaVehiculo;
    }

    public void setPlacaVehiculo(String placaVehiculo) {
        this.placaVehiculo = placaVehiculo;
    }
}

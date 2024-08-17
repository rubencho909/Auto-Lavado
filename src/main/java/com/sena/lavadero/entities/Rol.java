package com.sena.lavadero.entities;

import com.sena.lavadero.enums.RolNombre;
import jakarta.persistence.*;
import org.springframework.lang.NonNull;

@Entity
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NonNull
    @Enumerated(EnumType.STRING)
    @Column(unique = true)
    private RolNombre rolNombre;

    public Rol() {
    }

    public Rol(int id, @NonNull RolNombre rolNombre) {
        this.id = id;
        this.rolNombre = rolNombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public RolNombre getRolNombre() {
        return rolNombre;
    }

    public void setRolNombre(@NonNull RolNombre rolNombre) {
        this.rolNombre = rolNombre;
    }
}

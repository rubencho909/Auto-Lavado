package com.sena.lavadero.service;

import com.sena.lavadero.entities.Rol;
import com.sena.lavadero.entities.Usuario;
import com.sena.lavadero.enums.RolNombre;
import com.sena.lavadero.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RolService {

    @Autowired
    private RolRepository rolRepository;

    public List<Rol> listadoRoles(){
        return rolRepository.findAll();
    }

    public void guardarRol(Rol rol) {
        rolRepository.save(rol);
    }

    public Optional<Rol> getByRolNombre(RolNombre rolNombre){
        return rolRepository.findByRolNombre(rolNombre);
    }

    public boolean existsByRolNombre(RolNombre rolNombre) {
        return rolRepository.existsByRolNombre(rolNombre);
    }

    public Optional<Rol> getRolById(Integer id) {
        return rolRepository.findById(id);
    }

}

package com.sena.lavadero.repository;

import com.sena.lavadero.entities.Usuario;
import com.sena.lavadero.enums.RolNombre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByIdentificacion(String identificacion);

    @Query("SELECT u FROM Usuario u JOIN u.roles r WHERE r.rolNombre = :rol")
    List<Usuario> findByRol(@Param("rol") RolNombre rol);

}

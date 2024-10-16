package com.sena.lavadero.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.sena.lavadero.entities.Rol;
import com.sena.lavadero.entities.Usuario;
import com.sena.lavadero.enums.RolNombre;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UsuarioRepositoryTest {
    /*
        given - 'dado' Se especifica el escenario, las precondiciones.
        when - 'cuando' Condiciones de las acciones que se van a ejecutar.
        then - 'entonces' El resultado esperado, las validaciones a realizar.

        Ejemplo:

        GIVEN: Dado que el usuario no ha introducido ningun dato en el formulario
        WHEN: Cuando hace click en el boton guardar
        THEN: Entonces se seben mostrar los mensajes de validacion apropiados
    */

    @Autowired
    private UsuarioRepository usuarioRepository;

    private Usuario usuario;

    @BeforeEach
    void setup() {
        usuario = Usuario.builder()
                .nombre("James")
                .apellido("Rodriguez")
                .tipoIdentificacion("TI_CC")
                .identificacion("254256")
                .correo("james@correo.com")
                .username("jamesito")
                .password("123")
                .build();
    }

    @Test
    void testGuardarEmpleado() {
        // given: Dado un usuario
        Rol rolUser = new Rol(RolNombre.ROLE_USER);
        Usuario usuario1 = Usuario.builder()
                .nombre("John Andres")
                .apellido("Sierra Garcia")
                .tipoIdentificacion("TI_CC")
                .identificacion("741369")
                .correo("john@correo.com")
                .username("johncito")
                .password("123")
                .build();

        // when: Cuando realice la accion de guardar el usuario
        Usuario usuarioGuardado = usuarioRepository.save(usuario1);

        // then: Entonces validar las salidas
        assertThat(usuarioGuardado).isNotNull();
        assertThat(usuarioGuardado.getId()).isGreaterThan(0);
    }

    @Test
    void testListarEmpleados() {
        // given: Dado un empleado
        Usuario usuario1 = Usuario.builder()
                .nombre("Deisy")
                .apellido("Barbosa")
                .tipoIdentificacion("TI_CC")
                .identificacion("956754")
                .correo("deisy@correo.com")
                .username("deisy")
                .password("123")
                .build();
        usuarioRepository.save(usuario1);
        usuarioRepository.save(usuario);

        // when Cuando ejecutamos la accion de listar los usuarios
        List<Usuario> listaUsuarios = usuarioRepository.findAll();

        // then Verificamos la salida.
        // Primero la lista no debe ser null.
        // Segundo debe ser igual a 5 porque hay 3 usuarios ya registrados y dos que estamos pasando
        assertThat(listaUsuarios).isNotNull();
        assertThat(listaUsuarios.size()).isEqualTo(5);

    }
}

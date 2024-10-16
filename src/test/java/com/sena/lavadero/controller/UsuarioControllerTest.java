package com.sena.lavadero.controller;

import com.sena.lavadero.controllers.UsuarioController;
import com.sena.lavadero.entities.Rol;
import com.sena.lavadero.entities.Usuario;
import com.sena.lavadero.enums.RolNombre;
import com.sena.lavadero.service.RolService;
import com.sena.lavadero.service.UsuarioService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class UsuarioControllerTest {

    @Mock
    private UsuarioService usuarioService;

    @Mock
    private RolService rolService;

    @InjectMocks
    private UsuarioController usuarioController;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    public void testSaveUser() {
        // Simular el comportamiento del servicio
        Rol rolUser = new Rol(RolNombre.ROLE_USER);
        when(usuarioService.existsByUsername(anyString())).thenReturn(false);
        when(usuarioService.existsByIdentificacion(anyString())).thenReturn(false);
        when(rolService.getByRolNombre(RolNombre.ROLE_USER)).thenReturn(Optional.of(rolUser));
        when(passwordEncoder.encode(anyString())).thenReturn("encoded_password");

        // Preparar el Model y RedirectAttributes
        Model model = new ConcurrentModel();
        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();

        // Ejecutar el método del controlador
        String result = usuarioController.saveUser(
                "John",
                "Doe",
                "Cédula de Ciudadanía",
                "123456789",
                "john.doe@example.com",
                "johndoe",
                "password123",
                redirectAttributes,
                model
        );

        // Verificar que el usuario se guardó
        verify(usuarioService).guardarUsuario(any(Usuario.class));

        // Verificar el redireccionamiento
        assertEquals("redirect:/login", result);
        assertTrue(redirectAttributes.getFlashAttributes().containsKey("usuarioRegistrado"));
    }
}

package com.sena.lavadero.controllers;

import com.sena.lavadero.entities.Rol;
import com.sena.lavadero.entities.Usuario;
import com.sena.lavadero.enums.RolNombre;
import com.sena.lavadero.service.RolService;
import com.sena.lavadero.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private RolService rolService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("/registro")
    public String registrar() {
        return "registro";
    }

    @PostMapping("/save")
    public String saveUser(String nombre,
                           String apellido,
                           String tipoIdentificacion,
                           String identificacion,
                           String correo,
                           String username,
                           String password,
                           RedirectAttributes redirect,
                           Model model) {

        if(usuarioService.existsByUsername(username)) {
            model.addAttribute("usuarioRepetido", "El Usuario ya se encuentra registrado!");
            return "registro";
        }

        if(usuarioService.existsByIdentificacion(identificacion)) {
            model.addAttribute("identificacionRepetida", "Número de Identificación ya se encuentra registrado!");
            return "registro";
        }
        System.out.println("**** Correo: **** " + correo);
        if(usuarioService.existsByCorreo(correo)) {
            model.addAttribute("correoRepetido", "Correo Electrónico ya se encuentra registrado!");
            return "registro";
        }

        if(username.isEmpty() || password.isEmpty() || nombre.isEmpty() || apellido.isEmpty() ||
                tipoIdentificacion.isEmpty() || identificacion.isEmpty() || correo.isEmpty()) {
            model.addAttribute("camposVacios", "Los campos no pueden estar vacíos!");
            return "registro";
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setTipoIdentificacion(tipoIdentificacion);
        usuario.setIdentificacion(identificacion);
        usuario.setCorreo(correo);
        usuario.setUsername(username);
        usuario.setPassword(passwordEncoder.encode(password));

        Rol rolUser = rolService.getByRolNombre(RolNombre.ROLE_USER).get();
        //Rol rolAdmin = rolService.getByRolNombre(RolNombre.ROLE_ADMIN).get();

        Set<Rol> roles = new HashSet<Rol>();
        roles.add(rolUser);
        //roles.add(rolAdmin);

        usuario.setRoles(roles);

        usuarioService.guardarUsuario(usuario);

        redirect.addFlashAttribute("usuarioRegistrado", "Registro de Usuario Exitoso, Inicie Sesion");

        return "redirect:/login";

    }

}

package com.sena.lavadero.controllers;

import com.sena.lavadero.entities.Comentario;
import com.sena.lavadero.entities.Servicio;
import com.sena.lavadero.entities.Usuario;
import com.sena.lavadero.service.IComentarioService;
import com.sena.lavadero.service.IServiciosService;
import com.sena.lavadero.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/servicios")
@SessionAttributes("comentario")
public class ServicioController {

    @Autowired
    private IServiciosService iServiciosService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private IComentarioService iComentarioService;

    @GetMapping("/ver-comentarios/{id}")
    public String ser(@PathVariable Long id, Model model, Authentication auth) {

        if(auth != null) {
            String username = auth.getName();
            model.addAttribute("username", username);
            System.out.println("*** USERNAME *** " + username);
        }

        Servicio servicio = iServiciosService.servicioPorId(id);
        model.addAttribute("servicio", servicio);

        return "comentarios/verComentarios";
    }

    @GetMapping("/cargar-ser-para-comentar/{id}")
    public String serParaComentar(@PathVariable Long id, Model model) {

        // Obtenemos el servicio por ID
        Servicio servicio = iServiciosService.servicioPorId(id);

        // Instanciamos el comentario
        Comentario comentario = new Comentario();
        comentario.setServicio(servicio);
        model.addAttribute("comentario", comentario);
        model.addAttribute("servicio", servicio);
        return "comentarios/comentarioForm";
    }

    @PostMapping("/save-comentario")
    public String guardarComentario(Comentario comentario, Authentication auth,
                                    HttpSession session, RedirectAttributes redirect) {

        String username = auth.getName();
        Optional<Usuario> usuario = usuarioService.getByUsername(username);
        //System.out.println("*** USERNAME *** " + usuario.get().getUsername());
        comentario.setUsuario(usuario.get());
        iComentarioService.saveComentario(comentario);
        redirect.addFlashAttribute("comentarioGuardado", "Comentario Guardado Exitosamente");
        return "redirect:/";
    }

}

package com.sena.lavadero.controllers;

import com.sena.lavadero.entities.Comentario;
import com.sena.lavadero.entities.Usuario;
import com.sena.lavadero.service.IComentarioService;
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
@RequestMapping("/comentarios")
public class ComentarioController {

    @Autowired
    private IComentarioService iComentarioService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/editar-comentario/{id}")
    public String editarComentarioForm(@PathVariable(name = "id") Long id, Model model) {
        model.addAttribute("comentario", iComentarioService.buscarPorId(id));
        return "comentarios/editarComentarioForm";
    }

    @GetMapping("/editar")
    public String editar(@ModelAttribute("comentario")Comentario comentario,
                         RedirectAttributes redirect, Authentication auth, HttpSession session) {

        String username = auth.getName();
        Optional<Usuario> usuario = usuarioService.getByUsername(username);

        comentario.setUsuario(usuario.get());

        iComentarioService.saveComentario(comentario);

        redirect.addFlashAttribute("comentarioModificado", "Comentario Actualizado Exitosamente");

        return "redirect:/";
    }

    @GetMapping("/eliminar-comentario/{id}")
    public String eliminar(@PathVariable(name = "id") Long id, RedirectAttributes redirect) {
        iComentarioService.eliminarComentario(id);
        redirect.addFlashAttribute("comentarioEliminado", "Comentario Eliminado Exitosamente");
        return "redirect:/";
    }
}

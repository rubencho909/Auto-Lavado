package com.sena.lavadero.controllers;

import com.sena.lavadero.entities.Movimiento;
import com.sena.lavadero.entities.TipoConcepto;
import com.sena.lavadero.entities.Usuario;
import com.sena.lavadero.service.IMovimientoService;
import com.sena.lavadero.service.TipoConceptoService;
import com.sena.lavadero.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

@Controller
@RequestMapping("/movimiento")
public class MovimientoController {

    @Autowired
    private IMovimientoService iMovimientoService;

    @Autowired
    private TipoConceptoService tipoConceptoService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/gestion-nomina")
    public String listarMovimientos(Model model, Authentication auth) {

        if(auth != null) {
            String username = auth.getName();
            model.addAttribute("username", username);
        }

        model.addAttribute("movimientos", iMovimientoService.listarMovimientos());
        return "movimientos/gestionMovimientos";
    }

    @GetMapping("/movimiento-form")
    public String movimientoForm(Model model) {
        List<TipoConcepto> listaConceptos = tipoConceptoService.listadoConceptos();
        model.addAttribute("movimiento", new Movimiento());
        model.addAttribute("conceptos", listaConceptos);
        return "movimientos/movimientoForm";
    }

    @PostMapping("/save-movimiento")
    public String guardarMovimiento(Movimiento movimiento,
                                    RedirectAttributes redirect,
                                    Authentication auth) {

        if (auth != null) {
            String username = auth.getName();
            Usuario usuarioLogueado = usuarioService.getByUsername(username).orElse(null);
            if (usuarioLogueado != null) {
                movimiento.setUsuario(usuarioLogueado);
            }
        }

        iMovimientoService.save(movimiento);
        redirect.addFlashAttribute( "movGuardado", "Movimiento Guardado Exitosamente!");
        return "redirect:/movimiento/gestion-nomina";
    }

    @GetMapping("/eliminar-movimiento/{id}")
    public String eliminarMovimiento(@PathVariable Long id, RedirectAttributes redirect){
        iMovimientoService.eliminarMovimiento(id);
        redirect.addFlashAttribute( "movEliminado", "Movimiento Eliminado Exitosamente!");
        return "redirect:/movimiento/gestion-nomina";
    }


    @GetMapping("/editar-form/{id}")
    public String editarFormularioMovimiento(@PathVariable Long id, Model model) {

        Movimiento movimiento = null;
        if(id > 0) {
            movimiento = iMovimientoService.movimientoPorId(id);
            List<TipoConcepto> conceptos = tipoConceptoService.listadoConceptos();
            model.addAttribute("movimiento", movimiento);
            model.addAttribute("conceptos", conceptos);
        }
        return "movimientos/editarMovimiento";
    }

    @PostMapping("/editar-movimiento")
    public String editarMovimiento(@ModelAttribute("movimiento") Movimiento movimiento,
                                   RedirectAttributes redirectAttributes,
                                   Authentication auth) {

        Movimiento movimientoExistente = iMovimientoService.movimientoPorId(movimiento.getId());

        Usuario usuarioLogueado = null;
        if (auth != null) {
            String username = auth.getName();
            usuarioLogueado = usuarioService.getByUsername(username).orElse(null);
            if (usuarioLogueado != null) {
                movimiento.setUsuario(usuarioLogueado);
            }
        }

        if (movimientoExistente != null) {

            movimientoExistente.setTipoConcepto(movimiento.getTipoConcepto());
            movimientoExistente.setValor(movimiento.getValor());
            movimientoExistente.setFechaCreacion(movimiento.getFechaCreacion());
            movimientoExistente.setUsuario(usuarioLogueado);

            iMovimientoService.save(movimientoExistente);

            redirectAttributes.addFlashAttribute("movActualizado", "Movimiento actualizado exitosamente!");

            return "redirect:/movimiento/gestion-nomina";
        }

        redirectAttributes.addFlashAttribute("error", "Movimiento no encontrado!");
        return "redirect:/movimiento/gestion-nomina";
    }

}

package com.sena.lavadero.controllers;

import com.sena.lavadero.entities.Servicio;
import com.sena.lavadero.entities.TipoServicio;
import com.sena.lavadero.service.IServiciosService;
import com.sena.lavadero.service.ITipoServiciosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/tipos")
@SessionAttributes("serParaTipoServicio")
public class TipoServicioController {

    @Autowired
    ITipoServiciosService iTipoServiciosService;

    @Autowired
    IServiciosService iServiciosService;

    @GetMapping("/tipos-form")
    public String tiposForm(TipoServicio tipoServicio, Model model, @ModelAttribute("serParaTipoServicio")Servicio servicio) {
        model.addAttribute("tipo", new TipoServicio());
        model.addAttribute("ser", servicio);
        return "admin/tiposForm";
    }

    @PostMapping("/save")
    public String saveTipos(TipoServicio tipoServicio, RedirectAttributes redirect,
                            Model model, @ModelAttribute("serParaTipoServicio")Servicio servicio) {

        iTipoServiciosService.saveTipoServicio(tipoServicio);
        redirect.addFlashAttribute("tipoGuardado", "Tipo de Servicio Guardado Exitosamente!");
        //return "redirect:/tipos/tipos-form";
        return "redirect:/admin/gestion-servicios";
    }

    @GetMapping("/add-tipos/{id}")
    public String addTipoServicios(@PathVariable Long id, Model model) {
        Servicio servicio = iServiciosService.servicioPorId(id);
        model.addAttribute("tipo", new TipoServicio());
        model.addAttribute("servicio", servicio);
        return "admin/addTipoServiciosForm";
    }

}

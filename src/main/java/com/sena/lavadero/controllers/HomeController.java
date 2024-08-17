package com.sena.lavadero.controllers;

import com.sena.lavadero.entities.Servicio;
import com.sena.lavadero.service.IServiciosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    @Autowired
    private IServiciosService iServiciosService;

    @GetMapping("")
    public String home(Model model, Authentication auth) {

        if(auth != null) {
            String username = auth.getName();
            model.addAttribute("username", username);
        }

        model.addAttribute("servicios", iServiciosService.listarServicios());
        model.addAttribute("servicio", new Servicio());
        return "home";
    }

    @GetMapping("/buscar")
    public String buscarServicio(@RequestParam String titulo,
                                 @ModelAttribute("servicio") Servicio servicio, Model model) {

        Servicio ser = iServiciosService.servicioPorTitulo(titulo);

        if(titulo != null) {
            model.addAttribute("ser", ser);
        }

        if(ser == null) {
            model.addAttribute("serNoEncontrado", "Sin resultados ...");
        }

        return "serBuscador";
    }

}

package com.sena.lavadero.controllers;

import com.sena.lavadero.entities.Rol;
import com.sena.lavadero.entities.Servicio;
import com.sena.lavadero.entities.TipoServicio;
import com.sena.lavadero.entities.Usuario;
import com.sena.lavadero.enums.RolNombre;
import com.sena.lavadero.service.IServiciosService;
import com.sena.lavadero.service.ITipoServiciosService;
import com.sena.lavadero.service.RolService;
import com.sena.lavadero.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private IServiciosService iServiciosService;

    @Autowired
    private ITipoServiciosService iTipoServiciosService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private RolService rolService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("/servicio-form")
    public String servicioForm(Model model) {
        model.addAttribute("servicio", new Servicio());
        return "admin/servicioForm";
    }

    @PostMapping("/save-servicio")
    public String guardarServicio(@RequestParam(name="file")MultipartFile portada, Servicio servicio,
                                  RedirectAttributes redirect) {

        if(!portada.isEmpty()) {
            String ruta = "D://ESTUDIOS REALIZADOS//TECNOLOGIA ANALISIS SOFTWARE//uploads";
            String nombreUnico = UUID.randomUUID() + " " + portada.getOriginalFilename();

            try {
                byte[] bytes = portada.getBytes();
                Path rutaAbsoluta = Paths.get(ruta + "//" + nombreUnico);
                Files.write(rutaAbsoluta, bytes);
                servicio.setPortada(nombreUnico);

                iServiciosService.save(servicio);
                redirect.addFlashAttribute( "serGuardado", "Servicio Guardado Exitosamente!");
                redirect.addFlashAttribute("serParaTipoServicio", servicio);

            } catch (Exception e) {
                e.getCause().getMessage();
            }
        }
        return "redirect:/tipos/tipos-form";
    }

    @GetMapping("/gestion-servicios")
    public String listadoServicios(Model model){
        model.addAttribute("servicios", iServiciosService.listarServicios());
        return "admin/gestionServicios";
    }

    @GetMapping("/eliminar-servicio/{id}")
    public String eliminarServicio(@PathVariable Long id, RedirectAttributes redirect){
        iServiciosService.eliminarServicio(id);
        redirect.addFlashAttribute( "serEliminado", "Servicio Eliminado Exitosamente!");
        return "redirect:/admin/gestion-servicios";
    }

    @GetMapping("/editar-form/{id}")
    public String editarFormulario(@PathVariable Long id, Model model) {

        Servicio servicio = null;
        if(id > 0) {
            servicio = iServiciosService.servicioPorId(id);
            model.addAttribute("servicio", servicio);
        }
        return "admin/editarServicio";
    }

    @PostMapping("/editar-servicio")
    public String editarServicio(@RequestParam(name = "file") MultipartFile imagenPortada,
                                 Servicio ser, RedirectAttributes redirect,
                                 @ModelAttribute("servicio") Servicio servicio, Model model) {

        if(!imagenPortada.isEmpty()) {
            String ruta = "D://ESTUDIOS REALIZADOS//TECNOLOGIA ANALISIS SOFTWARE//uploads";
            String nombreUnico = UUID.randomUUID() + " " + imagenPortada.getOriginalFilename();

            try {
                byte[] bytes = imagenPortada.getBytes();
                Path rutaAbsoluta = Paths.get(ruta + "//" + nombreUnico);
                Files.write(rutaAbsoluta, bytes);
                servicio.setPortada(nombreUnico);

                iServiciosService.save(servicio);
                redirect.addFlashAttribute( "serEditado", "Servicio Editado Exitosamente!");

            } catch (Exception e) {
                e.getCause().getMessage();
            }
        }
        return "redirect:/admin/gestion-servicios";
    }

    @GetMapping("/editar-tipos/{id}")
    public String editarTipos(@PathVariable Long id, Model model) {
        Servicio servicioById = iServiciosService.servicioPorId(id);
        model.addAttribute("serEncontrado", servicioById);
        return "admin/edicionTipos";
    }

    @GetMapping("/cargar-tipo/{id}")
    public String cargarTipo(@PathVariable Long id, Model model) {
        TipoServicio tipoServicio = iTipoServiciosService.obtenerTipo(id);
        model.addAttribute("tipo", tipoServicio);
        return "/admin/editarTipoForm";
    }

    @PostMapping("/editar-tipos")
    public String editarTipo(@ModelAttribute("tipo") TipoServicio tipoServicio, RedirectAttributes redirect) {
        iTipoServiciosService.saveTipoServicio(tipoServicio);
        redirect.addFlashAttribute("tipoEditado", "Tipo de Servicio Editado Exitosamente!");
        return "redirect:/admin/gestion-servicios";
    }

    @GetMapping("/eliminar-tipos/{id}")
    public String eliminarTipoServicio(@PathVariable Long id, RedirectAttributes redirect) {
        iTipoServiciosService.eliminarTipoServicio(id);
        redirect.addFlashAttribute("tipoEliminado", "Tipo de Servicio Eliminado Exitosamente!");
        return "redirect:/admin/gestion-servicios";
    }

    @PostMapping("/save-tipo")
    public String saveTipos(TipoServicio tipoServicio, RedirectAttributes redirect, Model model) {

        iTipoServiciosService.saveTipoServicio(tipoServicio);
        redirect.addFlashAttribute("tipoGuardado", "Tipo de Servicio Guardado Exitosamente!");
        return "redirect:/admin/gestion-servicios";
    }

    @GetMapping("/gestion-usuarios")
    public String listadoUsuarios(Model model){
        model.addAttribute("usuarios", usuarioService.listadoUsuarios());
        return "admin/gestionUsuarios";
    }

    @GetMapping("/eliminar-usuario/{id}")
    public String eliminarUsuario(@PathVariable Long id, RedirectAttributes redirect){
        usuarioService.eliminarUsuario(id);
        redirect.addFlashAttribute( "userEliminado", "Usuario Eliminado Exitosamente!");
        return "redirect:/admin/gestion-usuarios";
    }

    @GetMapping("/editarUser-form/{id}")
    public String editarFormularioUser(@PathVariable Long id, Model model) {

        Usuario usuario = null;
        if(id > 0) {
            usuario = usuarioService.getUsuarioById(id).orElse(null);
            List<Rol> roles = rolService.listadoRoles();
            model.addAttribute("usuario", usuario);
            model.addAttribute("roles", roles);
        }
        return "admin/editarUsuario";
    }

    @PostMapping("/editar-usuario")
    public String editarUsuario(@ModelAttribute("usuario") Usuario usuario,
                                @RequestParam(value = "roles", required = false) List<Integer> rolesSeleccionados,
                                RedirectAttributes redirect) {

        Usuario usuarioExistente = usuarioService.getUsuarioById(usuario.getId()).orElse(null);
        if (usuarioExistente != null) {
            Set<Rol> roles = new HashSet<>();
            if (rolesSeleccionados != null) {
                for (Integer rolId : rolesSeleccionados) {
                    Rol rol = rolService.getRolById(rolId).orElse(null);
                    if (rol != null) {
                        roles.add(rol);
                    }
                }
            }

            // Asegurarse de que siempre tenga el rol USER
            Rol rolUser = rolService.getByRolNombre(RolNombre.ROLE_USER).orElse(null);
            if (rolUser != null) {
                roles.add(rolUser);
            }

            usuario.setRoles(roles);
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
            usuarioService.guardarUsuario(usuario);
            redirect.addFlashAttribute("userEditado", "Usuario Editado Exitosamente!");
        }

        return "redirect:/admin/gestion-usuarios";
    }

}
